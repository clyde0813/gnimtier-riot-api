package com.gnimtier.riot.service.tft;

import com.gnimtier.riot.client.RiotKrApiClient;
import com.gnimtier.riot.data.dto.riot.SummonerDto;
import com.gnimtier.riot.data.dto.tft.LeagueEntryResponseDto;
import com.gnimtier.riot.data.dto.tft.SummonerResponseDto;
import com.gnimtier.riot.data.entity.riot.Account;
import com.gnimtier.riot.data.entity.riot.Summoner;
import com.gnimtier.riot.data.entity.tft.TFTLeagueEntry;
import com.gnimtier.riot.data.repository.riot.AccountRepository;
import com.gnimtier.riot.data.repository.riot.SummonerRepository;
import com.gnimtier.riot.exception.CustomException;
import com.gnimtier.riot.service.riot.AccountService;
import com.gnimtier.riot.utils.RedisUtils;
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
    private final RedisUtils redisUtils;
    private final SummonerRepository summonerRepository;
    private final RiotKrApiClient riotKrApiClient;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final AccountRepository accountRepository;


    public SummonerResponseDto getSummonerResponseDto(String puuid, boolean refresh) {
        // 결과 없는 검색어 필터링
        isBannedKeyword(puuid);

        // 갱신 요청 O + 갱신 주기 확인
        if (refresh) {
            refresh = isRecentlyModified(accountRepository.findByPuuid(puuid));
        }

        // 갱신 요청 X + Redis에 캐싱된 데이터가 있는지 확인
        SummonerResponseDto redisDto = redisUtils.getSummoner(puuid);
        if (!refresh && redisDto != null) {
            LOGGER.info("[SummonerService] - getSummonerResponseDto : Getting Summoner from Redis - {}", puuid);
            return redisDto;
        }

        // 갱신 요청 valid + redis 캐싱 데이터가 있는 경우
        // cache 제거
        if (refresh && redisDto != null) {
            LOGGER.info("[SummonerService] - getSummonerResponseDto : Deleting Summoner from Redis - {}", puuid);
            redisUtils.deleteSummoner(puuid);
        }

        Account account = accountService.getAccount(puuid, refresh);
        Summoner summoner = getSummoner(puuid, refresh);
        Map<String, LeagueEntryResponseDto> leagueEntry = leagueService.getLeagueEntryResponseDto(puuid, refresh);
        SummonerResponseDto summonerResponseDto = convertToSummonerResponseDto(account, summoner, leagueEntry);

        // Redis에 캐싱
        redisUtils.setSummoner(account.getPuuid(), summonerResponseDto);
        redisUtils.setSummoner(account.getGameName() + "#" + account.getTagLine(), summonerResponseDto);

        return convertToSummonerResponseDto(account, summoner, leagueEntry);
    }

    //Leaderboard LeagueEntry 재사용을 위한 method
    @Deprecated
    public SummonerResponseDto getSummonerResponseDto(TFTLeagueEntry leagueEntry) {
        String redisKey = leagueEntry.getPuuid();

        //Redis에 캐싱된 데이터가 있는지 확인
        SummonerResponseDto redisDto = redisUtils.getSummoner(redisKey);
        if (redisDto != null) {
            return redisDto;
        }

        Account account = accountService.getAccount(leagueEntry.getPuuid(), false);
        Summoner summoner = getSummoner(leagueEntry.getPuuid(), false);
        SummonerResponseDto summonerResponseDto = convertToSummonerResponseDto(account, summoner, Map.of(leagueEntry.getQueueType(), leagueEntry.toDto()));

        // Redis에 캐싱
        redisUtils.setSummoner(account.getPuuid(), summonerResponseDto);
        redisUtils.setSummoner(account.getGameName() + "#" + account.getTagLine(), summonerResponseDto);

        return summonerResponseDto;
    }


    public SummonerResponseDto getSummonerResponseDto(String gameName, String tagLine, boolean refresh) {
        String redisKey = gameName + "#" + tagLine;

        // 결과 없는 검색어 필터링
        isBannedKeyword(redisKey);

        // 갱신 요청 O + 갱신 주기 확인
        if (refresh) {
            refresh = isRecentlyModified(accountRepository.findByGameNameAndTagLine(gameName, tagLine));
        }

        // 갱신 요청 X + Redis에 캐싱된 데이터가 있는지 확인
        SummonerResponseDto redisDto = redisUtils.getSummoner(redisKey);
        if (!refresh && redisDto != null) {
            LOGGER.info("[SummonerService] - getSummonerResponseDto : Getting Summoner from Redis - {}", redisKey);
            return redisDto;
        }

        // 갱신 요청 valid + redis 캐싱 데이터가 있는 경우
        // cache 제거
        if (refresh && redisDto != null) {
            LOGGER.info("[SummonerService] - getSummonerResponseDto : Deleting Summoner from Redis - {}", redisKey);
            redisUtils.deleteSummoner(redisKey);
        }

        Account account = accountService.getAccount(gameName, tagLine, refresh);
        Summoner summoner = getSummoner(account.getPuuid(), refresh);
        Map<String, LeagueEntryResponseDto> leagueEntry = leagueService.getLeagueEntryResponseDto(account.getPuuid(), refresh);
        SummonerResponseDto summonerResponseDto = convertToSummonerResponseDto(account, summoner, leagueEntry);

        // Redis에 캐싱
        redisUtils.setSummoner(account.getPuuid(), summonerResponseDto);
        redisUtils.setSummoner(redisKey, summonerResponseDto);

        return convertToSummonerResponseDto(account, summoner, leagueEntry);
    }


    public Summoner getSummoner(String puuid, boolean refresh) {
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

    public void isBannedKeyword(String keyword) {
        if (redisUtils.existsByKeyword(keyword)) {
            throw new CustomException("Not Found (Banned)", HttpStatus.NOT_FOUND);
        }
    }
}