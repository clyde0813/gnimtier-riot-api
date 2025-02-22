package com.gnimtier.riot.data.dto.tft;

import com.gnimtier.riot.data.entity.tft.LeagueEntry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeagueEntryDto {
    private String puuid;
    private String summonerId;
    private String queueType;
    private String tier;
    private String rank;
    private int leaguePoints;
    private int wins;
    private int losses;
    private Boolean hotStreak;
    private Boolean veteran;
    private Boolean inactive;
    private Boolean freshBlood;

    public LeagueEntry toEntity(String puuid, String season) {
        int tier = tierToInt(this.tier);
        int rank = romanToInt(this.rank);
        int scoreRank = scoreRank(tier, rank, this.leaguePoints);
        LeagueEntry leagueEntry = new LeagueEntry();
        leagueEntry.setId(season + "-" + summonerId + "-" + queueType);
        leagueEntry.setQueueType(queueType);
        leagueEntry.setSummonerId(summonerId);
        leagueEntry.setSeason(season);
        leagueEntry.setPuuid(puuid);
        leagueEntry.setTier(tier);
        leagueEntry.setRank(rank);
        leagueEntry.setLeaguePoints(leaguePoints);
        leagueEntry.setWins(wins);
        leagueEntry.setLosses(losses);
        leagueEntry.setHotStreak(hotStreak);
        leagueEntry.setVeteran(veteran);
        leagueEntry.setInactive(inactive);
        leagueEntry.setFreshBlood(freshBlood);
        leagueEntry.setRankScore(scoreRank);
        return leagueEntry;
    }

    private int tierToInt(String tier) {
        return switch (tier) {
            case "CHALLENGER" -> 10;
            case "GRANDMASTER" -> 9;
            case "MASTER" -> 8;
            case "DIAMOND" -> 7;
            case "EMERALD" -> 6;
            case "PLATINUM" -> 5;
            case "GOLD" -> 4;
            case "SILVER" -> 3;
            case "BRONZE" -> 2;
            case "IRON" -> 1;
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

    private int scoreRank(int tier, int rank, int leaguePoints) {
        return tier * 100000 + rank * 10000 + leaguePoints;
    }
}
