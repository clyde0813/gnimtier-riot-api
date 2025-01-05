package com.gnimtier.riot.service.tft;

import com.gnimtier.riot.data.dto.tft.request.PuuidListRequestDto;
import com.gnimtier.riot.data.dto.tft.response.SummonerLeaderboardResponseDto;
import org.springframework.data.domain.Pageable;

public interface LeaderboardService {
    SummonerLeaderboardResponseDto getTierLeaderboardByPuuids(PuuidListRequestDto puuidListRequestDto);
}
