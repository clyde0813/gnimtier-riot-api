package com.gnimtier.riot.data.dao.riot;

import com.gnimtier.riot.data.entity.riot.Summoner;

import java.util.Optional;

@Deprecated
public interface SummonerDAO {
    Summoner insertSummoner(Summoner summoner);
    Optional<Summoner> getSummonerById(String id);
    Optional<Summoner> getSummonerByPuuid(String puuid);
    Optional<Summoner> getSummonerByAccountId(String accountId);
//    Summoner updateSummoner(Summoner summoner);
}
