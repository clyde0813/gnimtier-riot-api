package com.gnimtier.riot.data.dto.riot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {
    private String puuid;
    private String gameName;
    private String tagLine;
}
