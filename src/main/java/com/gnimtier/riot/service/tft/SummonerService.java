package com.gnimtier.riot.service.tft;

import com.gnimtier.riot.data.dto.tft.response.SummonerResponseDto;

public interface SummonerService {
    SummonerResponseDto getByGameNameAndTagLine(String gameName, String tagLine);
    SummonerResponseDto getByPuuid(String puuid);
}
