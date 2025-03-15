package com.gnimtier.riot.data.entity.tft;

import com.gnimtier.riot.data.dto.tft.LeagueEntryResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tft_league_entry", indexes = {
        @Index(name = "idx_league_ranking", columnList = "rank_score DESC")
})
@EntityListeners(AuditingEntityListener.class)
public class TFTLeagueEntry {
    // season + summonerId + queueType로 유니크한 값
    @Id
    private String id;

    //Custom Column
    //season - float 형식이나 혹시 모를 상황에 대비하여 String으로 선언
    @Column(name = "season")
    private String season;

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

    //Custom Column
    // rankScore - tier, rank, leaguePoints를 합산한 값
    @Column(name = "rank_score")
    private int rankScore;

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

    @LastModifiedDate
    private LocalDateTime modifiedDate;

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
        return dto;
    }
}