package com.gnimtier.riot.service.tft;

import com.gnimtier.riot.data.dto.tft.response.LeagueEntryResponseDto;

import java.util.Map;

public interface LeagueService {
    Map<String, LeagueEntryResponseDto> getBySummonerId(String summonerId);
}
