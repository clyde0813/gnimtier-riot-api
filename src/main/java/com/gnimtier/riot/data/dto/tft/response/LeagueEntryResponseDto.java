package com.gnimtier.riot.data.dto.tft.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeagueEntryResponseDto {
    private int leaguePoints;
    private String rank;
    private int wins;
    private int losses;
    private Boolean veteran;
    private Boolean inactive;
    private Boolean freshBlood;
    private Boolean hotStreak;
    private String tier;
    private String leagueId;
    private String queueType;
}
