package com.gnimtier.riot.controller.tft;

import com.gnimtier.riot.data.dto.tft.response.SummonerResponseDto;
import com.gnimtier.riot.service.tft.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tft/summoners")
public class SummonerController {
    private final SummonerService summonerService;

    @GetMapping("/by-puuid/{puuid}")
    public SummonerResponseDto getSummonerByPuuid(
            @PathVariable("puuid") String puuid,
            @RequestParam(value = "refresh", required = false, defaultValue = "false") boolean refresh
    ) {
        return summonerService.getSummonerResponseDto(puuid, refresh);
    }

    @GetMapping("/by-riot-id/{gameName}/{tagLine}")
    public SummonerResponseDto getSummonerByRiotId(
            @PathVariable("gameName") String gameName,
            @PathVariable("tagLine") String tagLine,
            @RequestParam(value = "refresh", required = false, defaultValue = "false") boolean refresh

    ) {
        return summonerService.getSummonerResponseDto(gameName, tagLine, refresh);
    }
}
