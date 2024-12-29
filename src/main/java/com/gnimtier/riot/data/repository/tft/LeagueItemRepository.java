package com.gnimtier.riot.data.repository.tft;

import com.gnimtier.riot.data.entity.tft.LeagueItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueItemRepository extends JpaRepository<LeagueItem, Long> {
}
