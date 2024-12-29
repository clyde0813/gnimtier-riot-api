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
    private String id;
    private String accountId;
    private String name;
    private String profileIconId;
    private Long revisionDate;
    private Long summonerLevel;
    private AccountDto account;
}
