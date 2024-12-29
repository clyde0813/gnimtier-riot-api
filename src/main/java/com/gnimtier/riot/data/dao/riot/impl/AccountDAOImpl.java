package com.gnimtier.riot.data.dao.riot.impl;

import com.gnimtier.riot.data.dao.riot.AccountDAO;
import com.gnimtier.riot.data.entity.riot.Account;
import com.gnimtier.riot.data.repository.riot.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountDAOImpl implements AccountDAO {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountDAOImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account insertAccount(Account account) {
        Account savedAccount = accountRepository.save(account);

        return savedAccount;
    }

    @Override
    public Optional<Account> getAccountByPuuid(String puuid){
        Optional<Account> selectedAccount = accountRepository.findByPuuid(puuid);

        return selectedAccount;
    }
}
