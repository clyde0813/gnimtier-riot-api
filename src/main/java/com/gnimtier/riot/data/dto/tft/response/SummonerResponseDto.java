package com.gnimtier.riot.data.dto.tft.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SummonerResponseDto {
    private String id;
    private String accountId;
    private String puuid;
    private int profileIconId;
    private LocalDateTime revisionDate;
    private int summonerLevel;
    private LocalDateTime updatedAt;
    private LocalDateTime renewedAt;
    private String gameName;
    private String tagLine;
    private EntryDto entry;
}

