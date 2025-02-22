package com.gnimtier.riot.data.dto.riot;

import com.gnimtier.riot.data.entity.riot.Account;
import com.gnimtier.riot.data.repository.riot.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AccountDto {
    private String puuid;
    private String gameName;
    private String tagLine;

    public Account toEntity() {
        Account account = new Account();
        account.setPuuid(puuid);
        account.setGameName(gameName);
        account.setTagLine(tagLine);
        return account;
    }
}
