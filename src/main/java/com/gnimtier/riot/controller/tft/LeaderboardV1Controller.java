package com.gnimtier.riot.controller.tft;

import com.gnimtier.riot.data.dto.tft.request.PuuidListRequestDto;
import com.gnimtier.riot.data.dto.tft.response.SummonerLeaderboardResponseDto;
import com.gnimtier.riot.data.dto.tft.response.SummonerResponseDto;
import com.gnimtier.riot.service.tft.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tft/leaderboards")
public class LeaderboardV1Controller {
    private final LeaderboardService leaderboardService;

    @Autowired
    public LeaderboardV1Controller(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @PostMapping("/tier/by-puuid-list")
    public SummonerLeaderboardResponseDto getTierLeaderboardByPuuids(
            @RequestBody PuuidListRequestDto puuidListRequestDto
            ) {
        SummonerLeaderboardResponseDto summoners = leaderboardService.getTierLeaderboardByPuuids(puuidListRequestDto);
        return summoners;
    }
}
