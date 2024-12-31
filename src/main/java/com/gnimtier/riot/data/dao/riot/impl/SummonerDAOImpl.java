package com.gnimtier.riot.data.dao.riot.impl;

import com.gnimtier.riot.data.dao.riot.SummonerDAO;
import com.gnimtier.riot.data.entity.riot.Summoner;
import com.gnimtier.riot.data.repository.riot.AccountRepository;
import com.gnimtier.riot.data.repository.riot.SummonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.Optional;

@Deprecated
@Component
public class SummonerDAOImpl implements SummonerDAO {

    private final AccountRepository accountRepository;
    private SummonerRepository summonerRepository;

    @Autowired
    public SummonerDAOImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Summoner insertSummoner(Summoner summoner) {
        Summoner savedSummoner = summonerRepository.save(summoner);

        return savedSummoner;
    }

    @Override
    public Optional<Summoner> getSummonerById(String id) {
        Optional<Summoner> selectedSummoner = summonerRepository.findById(id);
        return selectedSummoner;
    }

    @Override
    public Optional<Summoner> getSummonerByPuuid(String puuid) {
        return summonerRepository.findSummonerByAccountPuuid(puuid);
    }

    @Override
    public Optional<Summoner> getSummonerByAccountId(String accountId) {
        return summonerRepository.findSummonerByAccountId(accountId);
    }

}
