package com.gnimtier.riot.data.dto.gnt;

import lombok.Data;

import java.util.List;

@Data
public class RankRequestDto {
    private List<String> puuidList;
    private String puuid;
}
