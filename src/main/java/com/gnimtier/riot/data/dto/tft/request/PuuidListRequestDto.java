package com.gnimtier.riot.data.dto.tft.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PuuidListRequestDto {
    private List<String> puuid;
    private int pageSize;
    private int page;
}