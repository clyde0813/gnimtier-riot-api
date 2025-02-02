package com.gnimtier.riot.data.dto.tft;

import com.gnimtier.riot.data.dto.tft.response.LeagueEntryResponseDto;
import com.gnimtier.riot.data.entity.tft.LeagueEntry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

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

    public LeagueEntry toEntity(String puuid) {
        LeagueEntry leagueEntry = new LeagueEntry();
        leagueEntry.setLeagueId(leagueId);
        leagueEntry.setQueueType(queueType);
        leagueEntry.setSummonerId(SummonerId);
        leagueEntry.setPuuid(puuid);
        leagueEntry.setTier(tierToInt(tier));
        leagueEntry.setRank(romanToInt(rank));
        leagueEntry.setLeaguePoints(leaguePoints);
        leagueEntry.setWins(wins);
        leagueEntry.setLosses(losses);
        leagueEntry.setHotStreak(hotStreak);
        leagueEntry.setVeteran(veteran);
        leagueEntry.setInactive(inactive);
        leagueEntry.setFreshBlood(freshBlood);
        return leagueEntry;
    }

    private int tierToInt(String tier) {
        return switch (tier) {
            case "CHALLENGER" -> 1;
            case "GRANDMASTER" -> 2;
            case "MASTER" -> 3;
            case "DIAMOND" -> 4;
            case "EMERALD" -> 5;
            case "PLATINUM" -> 6;
            case "GOLD" -> 7;
            case "SILVER" -> 8;
            case "BRONZE" -> 9;
            case "IRON" -> 10;
            case null, default -> 0;
        };
    }

    private int romanToInt(String roman) {
        if (Objects.equals(roman, "I")) {
            return 1;
        } else if (Objects.equals(roman, "II")) {
            return 2;
        } else if (Objects.equals(roman, "III")) {
            return 3;
        } else if (Objects.equals(roman, "IV")) {
            return 4;
        } else if (Objects.equals(roman, "V")) {
            return 5;
        }
        return 0;
    }
}
