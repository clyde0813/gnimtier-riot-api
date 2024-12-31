package com.gnimtier.riot.data.repository.riot;

import com.gnimtier.riot.data.entity.riot.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface SummonerRepository extends JpaRepository<Summoner, String> {
    Optional<Summoner> findById(String id);
    Optional<Summoner> findSummonerByAccountPuuid(String puuid);
    Optional<Summoner> findSummonerByAccountId(String accountId);
}
