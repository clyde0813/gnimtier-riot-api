package com.gnimtier.riot.data.dao.riot;

import com.gnimtier.riot.data.entity.riot.Summoner;

public interface SummonerDAO {
    Summoner insertSummoner(Summoner summoner);
    Summoner getSummonerById(String id);
    Summoner getSummonerByPuuid(String puuid);
    Summoner getSummonerByAccountId(String accountId);
    Summoner updateSummoner(Summoner summoner);
}
