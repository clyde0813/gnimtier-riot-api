package com.gnimtier.riot.data.dto.tft.response;

import com.gnimtier.riot.data.dto.riot.AccountDto;
import com.gnimtier.riot.data.entity.riot.Account;
import com.gnimtier.riot.data.entity.riot.Summoner;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class SummonerResponseDto extends AccountDto {
    private String id;
    private String accountId;
    private int profileIconId;
    private Long revisionDate;
    private Long summonerLevel;
    private Map<String, LeagueEntryResponseDto> entry;
}

