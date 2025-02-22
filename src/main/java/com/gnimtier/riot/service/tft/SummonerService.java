package com.gnimtier.riot.service.tft;

import com.gnimtier.riot.client.RiotKrApiClient;
import com.gnimtier.riot.data.dto.riot.SummonerDto;
import com.gnimtier.riot.data.dto.tft.response.SummonerResponseDto;
import com.gnimtier.riot.data.entity.riot.Account;
import com.gnimtier.riot.data.entity.riot.Summoner;
import com.gnimtier.riot.data.entity.tft.LeagueEntry;
import com.gnimtier.riot.data.repository.riot.SummonerRepository;
import com.gnimtier.riot.service.riot.AccountService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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


    public SummonerResponseDto getSummonerResponseDto(String puuid, boolean refresh) {
        Account account = accountService.getByPuuid(puuid, refresh);
        Summoner summoner = getByPuuid(puuid, refresh);
        SummonerResponseDto responseDto = new SummonerResponseDto();
        responseDto.setPuuid(account.getPuuid());
        responseDto.setGameName(account.getGameName());
        responseDto.setTagLine(account.getTagLine());
        responseDto.setId(summoner.getId());
        responseDto.setAccountId(summoner.getAccountId());
        responseDto.setProfileIconId(summoner.getProfileIconId());
        responseDto.setRevisionDate(summoner.getRevisionDate());
        responseDto.setSummonerLevel(summoner.getSummonerLevel());
        responseDto.setEntry(leagueService.getByPuuid(puuid, refresh));
        return responseDto;
    }

    //Leaderboard LeagueEntry 재사용을 위한 method
    public SummonerResponseDto getSummonerResponseDtoByLeagueEntry(LeagueEntry leagueEntry) {
        Account account = accountService.getByPuuid(leagueEntry.getPuuid(), false);
        Summoner summoner = getByPuuid(leagueEntry.getPuuid(), false);
        SummonerResponseDto responseDto = new SummonerResponseDto();
        responseDto.setPuuid(account.getPuuid());
        responseDto.setGameName(account.getGameName());
        responseDto.setTagLine(account.getTagLine());
        responseDto.setId(summoner.getId());
        responseDto.setAccountId(summoner.getAccountId());
        responseDto.setProfileIconId(summoner.getProfileIconId());
        responseDto.setRevisionDate(summoner.getRevisionDate());
        responseDto.setSummonerLevel(summoner.getSummonerLevel());
        responseDto.setEntry(Map.of(leagueEntry.getQueueType(), leagueEntry.toDto()));
        return responseDto;
    }


    public Summoner getByPuuid(String puuid, boolean refresh) {
        Optional<Summoner> selectedSummoner = summonerRepository.findByPuuid(puuid);
        // summoner
        if (selectedSummoner.isEmpty() || refresh) {
            SummonerDto summonerDto = riotKrApiClient.getSummonerByPuuid(puuid);
            return summonerRepository.save(summonerDto.toEntity());
        }
        return selectedSummoner.get();
    }
}