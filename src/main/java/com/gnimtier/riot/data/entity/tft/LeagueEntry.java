package com.gnimtier.riot.data.entity.tft;

import com.gnimtier.riot.data.dto.tft.LeagueEntryDto;
import com.gnimtier.riot.data.dto.tft.response.LeagueEntryResponseDto;
import com.gnimtier.riot.data.entity.riot.Summoner;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tft_league_entry")
public class LeagueEntry {
    //LeagueEntry - 라이엇에서 식별가능한 키를 제공하지 않음
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //leagueID - tier, leagueId, queue, name
    @Column(name = "league_id")
    private String leagueId;

    //queueType
    @Column(name = "queue_type")
    private String queueType;

    //summoner_id
    @Column(name = "summoner_id")
    private String summonerId;

    //Custom Column
    //Puuid
    @Column(name = "puuid")
    private String puuid;

    @Column(name = "tier")
    private int tier;

    //rank
    @Column(name = "rank")
    private int rank;

    //leaguePoints
    @Column(name = "league_points")
    private int leaguePoints;

    //wins
    @Column(name = "wins")
    private int wins;

    //losses
    @Column(name = "losses")
    private int losses;

    //veteran
    @Column(name = "veteran")
    private Boolean veteran;

    //inactive
    @Column(name = "inactive")
    private Boolean inactive;

    //freshBlood
    @Column(name = "fresh_blood")
    private Boolean freshBlood;

    //hotSteak
    @Column(name = "hot_streak")
    private Boolean hotStreak;

    public LeagueEntryResponseDto toDto() {
        LeagueEntryResponseDto dto = new LeagueEntryResponseDto();
        dto.setTier(tier);
        dto.setRank(rank);
        dto.setLeaguePoints(leaguePoints);
        dto.setWins(wins);
        dto.setLosses(losses);
        dto.setVeteran(veteran);
        dto.setInactive(inactive);
        dto.setFreshBlood(freshBlood);
        dto.setHotStreak(hotStreak);
//        dto.setLeagueId(leagueId);
//        dto.setQueueType(queueType);
        return dto;
    }
}