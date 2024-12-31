package com.gnimtier.riot.service.tft;

import com.gnimtier.riot.data.dto.tft.response.SummonerResponseDto;

public interface SummonerService {
    SummonerResponseDto getSummonerByGameName(String gameName, String tagLine);
}
