package com.gnimtier.riot.data.dto.tft.response;

import com.gnimtier.riot.data.dto.riot.AccountDto;
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
//    private String puuid;
    private int profileIconId;
    private Long revisionDate;
    private Long summonerLevel;
//    private LocalDateTime updatedAt;
//    private LocalDateTime renewedAt;
//    private String gameName;
//    private String tagLine;
    private Map<String, LeagueEntryResponseDto> entry;
}

