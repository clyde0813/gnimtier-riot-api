package com.gnimtier.riot.data.dto.tft.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntryDto {
    private LeagueEntryResponseDto RANKED_ENTRY;
}
