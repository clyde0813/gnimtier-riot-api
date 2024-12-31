package com.gnimtier.riot.service.riot;

import com.gnimtier.riot.data.dto.riot.AccountDto;
import com.gnimtier.riot.data.entity.riot.Account;

public interface AccountService {
    AccountDto getByPuuid(String puuid);
}
