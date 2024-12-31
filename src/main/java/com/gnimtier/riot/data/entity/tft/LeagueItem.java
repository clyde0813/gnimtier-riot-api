package com.gnimtier.riot.data.entity.tft;

import com.gnimtier.riot.data.entity.riot.Summoner;
import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a League Item entity in the context of Teamfight Tactics (TFT).
 * This entity stores data related to a summoner's rank and performance within a particular league.
 * It is associated with summoners, leagues, and contains detailed attributes such as rank,
 * points, wins, losses, and activity status.
 *
 * The LeagueItem class serves as a persisted representation of Riot's LeagueEntry concepts,
 * enabling database operations and integrations.
 *
 * Attributes:
 * - id: A unique identifier for the LeagueItem entry.
 * - league: The league in which the summoner participates, tied to tier, queue types, and league metadata.
 * - rank: The summoner's numerical rank within the league.
 * - summoner: The associated summoner whose data is captured by this entity.
 * - leaguePoints: The number of points the summoner has in the league.
 * - wins: The total number of wins achieved by the summoner in the league.
 * - losses: The total number of losses incurred by the summoner in the league.
 * - veteran: Indicates whether the summoner is a veteran in the league.
 * - inactive: Indicates whether the summoner is currently inactive.
 * - freshBlood: Indicates whether the summoner is a new player in the league.
 * - hotStreak: Indicates whether the summoner is currently on a win streak.
 * - createDate: A timestamp used for record-keeping purposes.
 */
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tft_league_item")
public class LeagueItem {
    //LeagueEntry - 라이엇에서 식별가능한 키를 제공하지 않음
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    //puuid - summoner 참조로 인하여 불필요
    @ManyToOne
    @JoinColumn(name = "account_puuid")
    private Account account;
    */

    //leagueID - tier, leagueId, queue, name
    @ManyToOne
    @JoinColumn(name = "tft_league_id")
    private League league;

    //rank
    @Column(name = "rank")
    private int rank;

    //summoner_id
    @ManyToOne
    @JoinColumn(name = "summoner_id")
    private Summoner summoner;

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
    private boolean veteran;

    //inactive
    @Column(name = "inactive")
    private boolean inactive;


    //freshBlood
    @Column(name = "fresh_blood")
    private boolean freshBlood;

    //hotSteak
    @Column(name = "hot_streak")
    private boolean hotStreak;

    //기록용
    @Column(name = "create_date")
    private long createDate;
}

/*
[
    {
        "puuid": "Ze55USHEQhcylaJsYBfzfrkprG7D5rHvwfFAgwfBCVCDaCp_qXSsPPHjMkzj3-Ug6o7XTirTZfQH7w",
        "leagueId": "844f523b-88a5-3999-ac6e-4fefe349874c",
        "queueType": "RANKED_TFT",
        "tier": "CHALLENGER",
        "rank": "I",
        "summonerId": "8SVR86Lpk18hbSGorwo9CDvwScUiqkcxc-QL6Nb5CdU4pMQpVeFvKHqT4A",
        "leaguePoints": 1710,
        "wins": 256,
        "losses": 188,
        "veteran": true,
        "inactive": false,
        "freshBlood": false,
        "hotStreak": true
    }
]
*/