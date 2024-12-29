package com.gnimtier.riot.data.dto.tft;

import com.gnimtier.riot.data.dto.riot.SummonerDto;
import com.gnimtier.riot.data.entity.tft.Tier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeagueDto {
    private TierDto tier;
    private QueueTypeDto queue;
    private String name;
}