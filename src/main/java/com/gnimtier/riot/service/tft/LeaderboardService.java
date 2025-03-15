package com.gnimtier.riot.service.tft;

import com.gnimtier.riot.data.dto.gnt.PageableRequestDto;
import com.gnimtier.riot.data.dto.gnt.PageableResponseDto;
import com.gnimtier.riot.data.dto.tft.SummonerResponseDto;
import com.gnimtier.riot.data.entity.riot.Account;
import com.gnimtier.riot.data.entity.riot.Summoner;
import com.gnimtier.riot.data.entity.tft.TFTLeagueEntry;
import com.gnimtier.riot.data.repository.riot.AccountRepository;
import com.gnimtier.riot.data.repository.tft.LeagueEntryRepository;
import com.gnimtier.riot.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaderboardService {
    private final SummonerService summonerService;
    private final RedisUtils redisUtils;
    private final AccountRepository accountRepository;
    private final LeagueEntryRepository leagueEntryRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(LeaderboardService.class);

    @Deprecated
    public PageableResponseDto<SummonerResponseDto> getTierLeaderboardByPuuidList(PageableRequestDto<String> puuidListRequestDto) {
        LOGGER.info("[getTierLeaderboardByPuuidList] - getting tier leaderboard");

        //Redis Cache 확인
        LOGGER.info("[getTierLeaderboardByPuuidList] - checking Redis");
        PageableResponseDto<SummonerResponseDto> redisDto = redisUtils.getLeaderboard(puuidListRequestDto.toString());
        if (redisDto != null) {
            LOGGER.info("[getTierLeaderboardByPuuidList] - cache found");
            //Redis Cache 있음
            return redisDto;
        }

        //Redis Cache 없음
        LOGGER.info("[getTierLeaderboardByPuuidList] - cache not found");
        Pageable pageable = PageRequest.of(puuidListRequestDto.getPage(), puuidListRequestDto.getPageSize());
        Page<TFTLeagueEntry> sortedLeagueEntry = leagueEntryRepository.getSortedLeagueEntryByTier(puuidListRequestDto.getData(), pageable);
        PageableResponseDto<SummonerResponseDto> responseDto = new PageableResponseDto<>();
        List<SummonerResponseDto> summonerResponseDtoList = new ArrayList<>();
        sortedLeagueEntry.forEach(TFTLeagueEntry -> summonerResponseDtoList.add(summonerService.getSummonerResponseDto(TFTLeagueEntry)));

        responseDto.setData(summonerResponseDtoList);
        responseDto.setPageSize(sortedLeagueEntry.getSize());
        responseDto.setPage(sortedLeagueEntry.getNumber());
        responseDto.setHasNext(sortedLeagueEntry.hasNext());
        responseDto.setHasPrevious(sortedLeagueEntry.hasPrevious());

        LOGGER.info("[getTierLeaderboardByPuuidList] - setting Redis");
        redisUtils.setLeaderboard(puuidListRequestDto.toString(), responseDto);
        LOGGER.info("[getTierLeaderboardByPuuidList] - set Redis");

        LOGGER.info("[getTierLeaderboardByPuuidList] - getting tier leaderboard end");
        return responseDto;
    }

    public PageableResponseDto<SummonerResponseDto> getRankLeaderboard(PageableRequestDto<String> puuidListRequestDto) {
        LOGGER.info("[getRankLeaderboard] - getting rank leaderboard");

        //Redis Cache 확인
        LOGGER.info("[getRankLeaderboard] - checking Redis");
        PageableResponseDto<SummonerResponseDto> redisDto = redisUtils.getLeaderboard(puuidListRequestDto.toString());
        if (redisDto != null) {
            LOGGER.info("[getRankLeaderboard] - cache found");
            //Redis Cache 있음
            return redisDto;
        }

        //Redis Cache 없음
        LOGGER.info("[getRankLeaderboard] - cache not found");
        Pageable pageable = PageRequest.of(puuidListRequestDto.getPage(), puuidListRequestDto.getPageSize());
        Page<Account> sortedAccount = accountRepository.getSortedAccountByTier(puuidListRequestDto.getData(), pageable);
        PageableResponseDto<SummonerResponseDto> responseDto = new PageableResponseDto<>();
        List<SummonerResponseDto> summonerResponseDtoList = new ArrayList<>();

        sortedAccount.forEach(Account -> summonerResponseDtoList.add(summonerService.getSummonerResponseDto(Account.getPuuid(), false)));

        responseDto.setData(summonerResponseDtoList);
        responseDto.setPageSize(sortedAccount.getNumberOfElements());
        responseDto.setPage(sortedAccount.getNumber());
        responseDto.setHasNext(sortedAccount.hasNext());
        responseDto.setHasPrevious(sortedAccount.hasPrevious());

        LOGGER.info("[getTierLeaderboardByPuuidList] - setting Redis");
        redisUtils.setLeaderboard(puuidListRequestDto.toString(), responseDto);
        LOGGER.info("[getTierLeaderboardByPuuidList] - set Redis");

        LOGGER.info("[getTierLeaderboardByPuuidList] - getting tier leaderboard end");
        return responseDto;
    }
}
