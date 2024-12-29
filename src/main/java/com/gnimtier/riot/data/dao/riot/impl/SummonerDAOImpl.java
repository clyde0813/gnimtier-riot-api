package com.gnimtier.riot.data.dao.riot.impl;

import com.gnimtier.riot.data.dao.riot.SummonerDAO;
import com.gnimtier.riot.data.repository.riot.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SummonerDAOImpl extends SummonerDAO {

    private final AccountRepository accountRepository;

    @Autowired
    public SummonerDAOImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    //Override해서 구현해야함
}
