package com.gnimtier.riot.data.dto.riot;

import com.gnimtier.riot.data.entity.riot.Summoner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SummonerDto {
    private String accountId;
    private int profileIconId;
    private Long revisionDate;
    private String id;
    private String puuid;
    private Long summonerLevel;

    public Summoner toEntity() {
        Summoner summoner = new Summoner();
        summoner.setId(id);
        summoner.setAccountId(accountId);
        summoner.setProfileIconId(profileIconId);
        summoner.setRevisionDate(revisionDate);
        summoner.setSummonerLevel(summonerLevel);
        summoner.setPuuid(puuid);
        return summoner;
    }
}
