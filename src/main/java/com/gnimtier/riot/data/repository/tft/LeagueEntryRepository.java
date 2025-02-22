package com.gnimtier.riot.data.repository.tft;

import com.gnimtier.riot.data.entity.riot.Summoner;
import com.gnimtier.riot.data.entity.tft.LeagueEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LeagueEntryRepository extends JpaRepository<LeagueEntry, Long> {
    Optional<LeagueEntry> findById(String id);

    List<LeagueEntry> findAllByPuuid(String puuid);

    @Query("""
                select l
                from LeagueEntry l
                where l.puuid in :puuids
                order by l.rankScore desc
            """)
    Page<LeagueEntry> getSortedLeagueEntryByTier(
            @Param("puuids") List<String> puuidList, Pageable pageable
    );
}
