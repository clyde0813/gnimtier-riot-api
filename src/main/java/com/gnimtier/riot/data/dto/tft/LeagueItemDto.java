package com.gnimtier.riot.data.dto.tft;

import com.gnimtier.riot.data.dto.riot.SummonerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeagueItemDto {
    private LeagueDto league;
    private int rank;
    private SummonerDto summoner;
    private int leaguePoints;
    private int wins;
    private int losses;
    private boolean veteran;
    private boolean inactive;
    private boolean freshBlood;
    private boolean hotStreak;
}
