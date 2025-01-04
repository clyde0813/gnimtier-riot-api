package com.gnimtier.riot.controller.riot;

import com.gnimtier.riot.data.dto.riot.response.SummonerResponseDto;
import com.gnimtier.riot.service.riot.SummonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/summoners")
public class SummonerV1Controller {
    private final SummonerService summonerService;

    @Autowired
    public SummonerV1Controller(SummonerService summonerService) {
        this.summonerService = summonerService;
    }

    @GetMapping("/by-riot-id/{gameName}/{tagLine}")
    public SummonerResponseDto getSummonerByGameNameAndTagLine(@PathVariable String gameName, @PathVariable String tagLine) {
        return summonerService.getByGameNameAndTagLine(gameName, tagLine);
    }
}
