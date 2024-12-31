package com.gnimtier.riot.service.tft;

import com.gnimtier.riot.data.dto.tft.response.SummonerResponseDto;

public interface SummonerService {
    SummonerResponseDto getByGameName(String gameName, String tagLine);
}
