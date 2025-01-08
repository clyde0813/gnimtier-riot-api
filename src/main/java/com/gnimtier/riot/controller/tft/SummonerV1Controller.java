package com.gnimtier.riot.controller.tft;

import com.gnimtier.riot.data.dto.tft.response.SummonerResponseDto;
import com.gnimtier.riot.service.tft.SummonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tft/summoners")
public class SummonerV1Controller {
    private final SummonerService summonerService;

    @Autowired
    public SummonerV1Controller(SummonerService summonerService) {
        this.summonerService = summonerService;
    }

    @GetMapping("/by-puuid/{puuid}")
    public SummonerResponseDto getSummonerByPuuid(@PathVariable("puuid") String puuid) {
        return summonerService.getByPuuid(puuid);
    }

    @PostMapping("/by-puuid-list")
    public List<SummonerResponseDto> getSummonersByPuuidList(@RequestBody List<String> puuidList) {
        return summonerService.getByPuuidList(puuidList);
    }

    //gameName, tagLine ArrayList로 한번에 다수의 계정 저장 controller
    @PostMapping("/by-gameName-list")
    public List<String> getSummonersByGameNameList(@RequestBody List<List<String>> gameNameList) {
        return summonerService.getByGameNameList(gameNameList);
    }

    @GetMapping("/by-riot-id/{gameName}/{tagLine}")
    public SummonerResponseDto getSummonerByGameNameAndTagLine(@PathVariable String gameName, @PathVariable String tagLine) {
        return summonerService.getByGameNameAndTagLine(gameName, tagLine);
    }
}
