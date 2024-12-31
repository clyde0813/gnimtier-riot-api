package com.gnimtier.riot.data.dto.riot;

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
    private String profileIconId;
    private Long revisionDate;
    private String id;
    private String puuid;
    private Long summonerLevel;
}
