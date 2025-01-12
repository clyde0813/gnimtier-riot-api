package com.gnimtier.riot.controller.tft;

import com.gnimtier.riot.data.dto.tft.request.PuuidListRequestDto;
import com.gnimtier.riot.data.dto.tft.response.SummonerLeaderboardResponseDto;
import com.gnimtier.riot.service.tft.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tft/leaderboards")
public class LeaderboardV1Controller {
    private final LeaderboardService leaderboardService;

    @Autowired
    public LeaderboardV1Controller(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @PostMapping("/by-puuid-list")
    public SummonerLeaderboardResponseDto getTierLeaderboardByPuuids(@RequestBody PuuidListRequestDto puuidListRequestDto) {
        SummonerLeaderboardResponseDto summoners;
        switch (puuidListRequestDto.getSortBy()) {
            case "tier":
                summoners = leaderboardService.getTierLeaderboardByPuuids(puuidListRequestDto);
                break;
            default:
                summoners = leaderboardService.getTierLeaderboardByPuuids(puuidListRequestDto);
                break;
        }
        return summoners;
    }
}
