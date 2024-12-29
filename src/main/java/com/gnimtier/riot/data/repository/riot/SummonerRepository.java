package com.gnimtier.riot.data.repository.riot;

import com.gnimtier.riot.data.entity.riot.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SummonerRepository extends JpaRepository<Summoner, String> {
}
