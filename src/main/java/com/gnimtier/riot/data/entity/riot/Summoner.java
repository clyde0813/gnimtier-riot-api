package com.gnimtier.riot.data.entity.riot;

import com.gnimtier.riot.data.dto.riot.SummonerDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "summoner")
public class Summoner {
    @Id
    private String id;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "profile_icon_id")
    private int profileIconId;

    @Column(name = "revision_date")
    private long revisionDate;

    @Column(name = "summoner_level")
    private long summonerLevel;

    @Column(name = "puuid")
    private String puuid;

    public SummonerDto toDto() {
        SummonerDto summonerDto = new SummonerDto();
        summonerDto.setId(id);
        summonerDto.setAccountId(accountId);
        summonerDto.setProfileIconId(profileIconId);
        summonerDto.setRevisionDate(revisionDate);
        summonerDto.setSummonerLevel(summonerLevel);
        summonerDto.setPuuid(puuid);
        return summonerDto;
    }
}
