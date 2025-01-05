package com.gnimtier.riot.data.repository.riot;

import com.gnimtier.riot.data.entity.riot.Summoner;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface SummonerRepository extends JpaRepository<Summoner, String> {
    Optional<Summoner> findByAccountPuuid(String puuid);
}
