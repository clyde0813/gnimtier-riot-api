package com.gnimtier.riot.service.tft;

import com.gnimtier.riot.client.RiotKrApiClient;
import com.gnimtier.riot.data.dto.riot.SummonerDto;
import com.gnimtier.riot.data.dto.tft.response.LeagueEntryResponseDto;
import com.gnimtier.riot.data.dto.tft.response.SummonerResponseDto;
import com.gnimtier.riot.data.entity.riot.Account;
import com.gnimtier.riot.data.entity.riot.Summoner;
import com.gnimtier.riot.data.entity.tft.LeagueEntry;
import com.gnimtier.riot.data.repository.riot.AccountRepository;
import com.gnimtier.riot.data.repository.riot.SummonerRepository;
import com.gnimtier.riot.exception.CustomException;
import com.gnimtier.riot.service.riot.AccountService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SummonerService {
    private final AccountService accountService;
    private final LeagueService leagueService;
    private final SummonerRepository summonerRepository;
    private final RiotKrApiClient riotKrApiClient;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final AccountRepository accountRepository;


    public SummonerResponseDto getSummonerResponseDto(String puuid, boolean refresh) {
        if (refresh) {
            refresh = isRecentlyModified(accountRepository.findByPuuid(puuid));
        }
        Account account = accountService.getAccount(puuid, refresh);
        Summoner summoner = getSummonerByPuuid(puuid, refresh);
        Map<String, LeagueEntryResponseDto> leagueEntry = leagueService.getLeagueEntryResponseDto(puuid, refresh);
        return convertToSummonerResponseDto(account, summoner, leagueEntry);
    }

    //Leaderboard LeagueEntry 재사용을 위한 method
    public SummonerResponseDto getSummonerResponseDto(LeagueEntry leagueEntry) {
        Account account = accountService.getAccount(leagueEntry.getPuuid(), false);
        Summoner summoner = getSummonerByPuuid(leagueEntry.getPuuid(), false);
        return convertToSummonerResponseDto(account, summoner, Map.of(leagueEntry.getQueueType(), leagueEntry.toDto()));
    }


    public SummonerResponseDto getSummonerResponseDto(String gameName, String tagLine, boolean refresh) {
        if (refresh) {
            refresh = isRecentlyModified(accountRepository.findByGameNameAndTagLine(gameName, tagLine));
        }
        Account account = accountService.getAccount(gameName, tagLine, refresh);
        Summoner summoner = getSummonerByPuuid(account.getPuuid(), refresh);
        Map<String, LeagueEntryResponseDto> leagueEntry = leagueService.getLeagueEntryResponseDto(account.getPuuid(), refresh);
        return convertToSummonerResponseDto(account, summoner, leagueEntry);
    }


    public Summoner getSummonerByPuuid(String puuid, boolean refresh) {
        Optional<Summoner> selectedSummoner = summonerRepository.findByPuuid(puuid);
        // summoner
        if (selectedSummoner.isEmpty() || refresh) {
            SummonerDto summonerDto = riotKrApiClient.getSummonerByPuuid(puuid);
            LOGGER.info("[SummonerService] - getSummonerByPuuid : getting Summoner from api done! - {} ", summonerDto.getPuuid());
            return summonerRepository.save(summonerDto.toEntity());
        }
        LOGGER.info("[SummonerService] - getSummonerByPuuid : getting Summoner from DB done! - {}", selectedSummoner
                .get()
                .getPuuid());
        return selectedSummoner.get();
    }

    public SummonerResponseDto convertToSummonerResponseDto(Account account, Summoner summoner, Map<String, LeagueEntryResponseDto> leagueEntry) {
        SummonerResponseDto summonerResponseDto = new SummonerResponseDto();
        summonerResponseDto.setPuuid(account.getPuuid());
        summonerResponseDto.setGameName(account.getGameName());
        summonerResponseDto.setTagLine(account.getTagLine());
        summonerResponseDto.setId(summoner.getId());
        summonerResponseDto.setAccountId(summoner.getAccountId());
        summonerResponseDto.setProfileIconId(summoner.getProfileIconId());
        summonerResponseDto.setRevisionDate(summoner.getRevisionDate());
        summonerResponseDto.setSummonerLevel(summoner.getSummonerLevel());
        summonerResponseDto.setEntry(leagueEntry);
        return summonerResponseDto;
    }

    public boolean isRecentlyModified(Optional<Account> account) {
        if (account.isEmpty()) {
            return true;
        }
        LocalDateTime modifiedDate = account
                .get()
                .getModifiedDate();
        if (!modifiedDate.isBefore(LocalDateTime
                .now()
                .minusMinutes(10))) {
            throw new CustomException("Too Many Requests", HttpStatus.TOO_MANY_REQUESTS);
        }
        return true;
    }
}