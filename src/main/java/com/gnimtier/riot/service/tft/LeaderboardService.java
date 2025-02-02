package com.gnimtier.riot.service.tft;

import com.gnimtier.riot.data.dto.tft.PageableDto;
import com.gnimtier.riot.data.dto.tft.response.SummonerResponseDto;
import com.gnimtier.riot.data.entity.tft.LeagueEntry;
import com.gnimtier.riot.data.repository.tft.LeagueEntryRepository;
import com.gnimtier.riot.service.riot.AccountService;
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
    private final LeagueEntryRepository leagueEntryRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(LeaderboardService.class);
    private final AccountService accountService;

    public PageableDto<SummonerResponseDto> getTierLeaderboardByPuuidList(PageableDto<String> puuidListRequestDto) {
        LOGGER.info("[getTierLeaderboardByPuuidList] - getting tier leaderboard");
        Pageable pageable = PageRequest.of(puuidListRequestDto.getPage(), puuidListRequestDto.getPageSize());
        Page<LeagueEntry> sortedLeagueEntry = leagueEntryRepository.getSortedLeagueEntryByTier(puuidListRequestDto.getData(), pageable);
        PageableDto<SummonerResponseDto> responseDto = new PageableDto<>();
        List<SummonerResponseDto> summonerResponseDtoList = new ArrayList<>();
        sortedLeagueEntry.forEach(LeagueEntry -> {
            summonerResponseDtoList.add(summonerService.getSummonerResponseDtoByLeagueEntry(LeagueEntry));
        });
        responseDto.setData(summonerResponseDtoList);
        responseDto.setPageSize(pageable.getPageSize());
        responseDto.setPage(pageable.getPageNumber());
        LOGGER.info("[getTierLeaderboardByPuuidList] - getting tier leaderboard end");
        return responseDto;
    }
}
