package com.gnimtier.riot.service.tft;

import com.gnimtier.riot.data.dto.tft.response.SummonerResponseDto;

import java.util.List;

public interface SummonerService {
    SummonerResponseDto getByGameNameAndTagLine(String gameName, String tagLine);

    SummonerResponseDto getByPuuid(String puuid);

    List<SummonerResponseDto> getByPuuidList(List<String> puuidList);

    List<String> getByGameNameList(List<List<String>> gameNameList);
}
