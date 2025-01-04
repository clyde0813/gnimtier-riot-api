package com.gnimtier.riot.data.repository.tft;

import com.gnimtier.riot.data.entity.tft.Tier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TierRepository extends JpaRepository<Tier, String> {
    Optional<Tier> findByName(String name);
}
