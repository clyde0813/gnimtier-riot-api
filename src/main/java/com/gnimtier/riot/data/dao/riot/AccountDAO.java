package com.gnimtier.riot.data.dao.riot;

import com.gnimtier.riot.data.entity.riot.Account;

import java.util.Optional;

public interface AccountDAO {
    Account insertAccount(Account account);
    Optional<Account> getAccountByPuuid(String puuid);
//    Account updateAccount(Account account);
}
