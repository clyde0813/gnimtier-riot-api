package com.gnimtier.riot.data.repository.tft;

import com.gnimtier.riot.data.entity.tft.League;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeagueRepository extends JpaRepository<League, String> {
    Optional<League> findById(String id);
}
