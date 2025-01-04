package com.gnimtier.riot.data.repository.tft;

import com.gnimtier.riot.data.entity.tft.QueueType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QueueTypeRepository extends JpaRepository<QueueType, String> {
    Optional<QueueType> findByName(String name);
}
