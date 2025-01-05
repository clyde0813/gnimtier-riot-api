package com.gnimtier.riot.data.dto.tft;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeagueDto {
    private int tier;
    private QueueTypeDto queue;
    private String name;
}