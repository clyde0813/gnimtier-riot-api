package com.gnimtier.riot.service.riot;

import com.gnimtier.riot.data.dto.riot.response.SummonerResponseDto;

public interface SummonerService {
    SummonerResponseDto getByGameNameAndTagLine(String gameName, String tagLine);
}
