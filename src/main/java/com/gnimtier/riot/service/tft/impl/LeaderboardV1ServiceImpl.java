package com.gnimtier.riot.service.tft.impl;

import com.gnimtier.riot.data.dto.tft.request.PuuidListRequestDto;
import com.gnimtier.riot.data.dto.tft.response.SummonerLeaderboardResponseDto;
import com.gnimtier.riot.data.dto.tft.response.SummonerResponseDto;
import com.gnimtier.riot.data.entity.riot.Summoner;
import com.gnimtier.riot.data.repository.tft.LeagueEntryRepository;
import com.gnimtier.riot.service.tft.LeaderboardService;
import org.hibernate.annotations.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeaderboardV1ServiceImpl implements LeaderboardService {
    private final SummonerV1ServiceImpl summonerService;
    private final LeagueEntryRepository leagueEntryRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(LeaderboardV1ServiceImpl.class);

    @Autowired
    public LeaderboardV1ServiceImpl(SummonerV1ServiceImpl summonerService, LeagueEntryRepository leagueEntryRepository) {
        this.summonerService = summonerService;
        this.leagueEntryRepository = leagueEntryRepository;
    }


    @Override
    public SummonerLeaderboardResponseDto getTierLeaderboardByPuuids(PuuidListRequestDto puuidListRequestDto) {
        Pageable pageable = PageRequest.of(puuidListRequestDto.getPage(), puuidListRequestDto.getPageSize());
        Page<Summoner> sortedSummoners = leagueEntryRepository.findSortedSummonersByPuuidsUsingTier(puuidListRequestDto.getPuuid(), pageable);
        SummonerLeaderboardResponseDto responseDto = new SummonerLeaderboardResponseDto();
        List<SummonerResponseDto> summonerResponseDtoList = new ArrayList<>();
        sortedSummoners.forEach(summoner -> {
            summonerResponseDtoList.add(summonerService.getSummonerResponseDto(summoner));
        });
        responseDto.setLeaderboard(summonerResponseDtoList);
        responseDto.setPageSize(pageable.getPageSize());
        responseDto.setPage(pageable.getPageNumber());
        return responseDto;
    }
}
