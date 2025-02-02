package com.gnimtier.riot.data.repository.tft;

import com.gnimtier.riot.data.entity.riot.Summoner;
import com.gnimtier.riot.data.entity.tft.LeagueEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeagueEntryRepository extends JpaRepository<LeagueEntry, Long> {
    List<LeagueEntry> findAllByPuuid(String puuid);

    @Query("""
    select l
    from LeagueEntry l
    where l.puuid in :puuids and
    l.queueType = "RANKED_TFT"
    order by l.tier ASC, l.rank ASC, l.leaguePoints DESC
""")
    Page<LeagueEntry> getSortedLeagueEntryByTier(
        @Param("puuids") List<String> puuidList, Pageable pageable
    );
}
