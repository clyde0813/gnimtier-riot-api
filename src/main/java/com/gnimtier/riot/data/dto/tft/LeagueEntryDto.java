package com.gnimtier.riot.data.dto.tft;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeagueEntryDto {
    private String puuid;
    private String leagueId;
    private String SummonerId;
    private String queueType;
    private int ratedRating;
    private String tier;
    private String rank;
    private int leaguePoints;
    private int wins;
    private int losses;
    private Boolean hotStreak;
    private Boolean veteran;
    private Boolean inactive;
    private Boolean freshBlood;
}
