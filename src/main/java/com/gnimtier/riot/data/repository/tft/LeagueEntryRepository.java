package com.gnimtier.riot.data.repository.tft;

import com.gnimtier.riot.data.entity.tft.LeagueEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeagueEntryRepository extends JpaRepository<LeagueEntry, Long> {
    List<LeagueEntry> findAllBySummonerId(String summonerId);
}
