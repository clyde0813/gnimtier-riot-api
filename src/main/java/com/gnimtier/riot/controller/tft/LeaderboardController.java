package com.gnimtier.riot.controller.tft;

import com.gnimtier.riot.data.dto.tft.PageableDto;
import com.gnimtier.riot.data.dto.tft.response.SummonerResponseDto;
import com.gnimtier.riot.service.tft.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tft/leaderboards")
public class LeaderboardController {
    private final LeaderboardService leaderboardService;

    @Autowired
    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @PostMapping("/by-puuid-list")
    public PageableDto<SummonerResponseDto> getTierLeaderboardByPuuidList(@RequestBody PageableDto<String> puuidListRequestDto) {
        PageableDto<SummonerResponseDto> summoners;
        switch (puuidListRequestDto.getSortBy()) {
            case "tier":
                summoners = leaderboardService.getTierLeaderboardByPuuidList(puuidListRequestDto);
                break;
            default:
                summoners = leaderboardService.getTierLeaderboardByPuuidList(puuidListRequestDto);
                break;
        }
        return summoners;
    }
}
