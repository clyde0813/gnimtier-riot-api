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
    List<LeagueEntry> findAllBySummonerId(String summonerId);
    List<LeagueEntry> findAllBySummoner(Summoner summoner);

    @Query("""
    select l.summoner
    from LeagueEntry l
    where l.summoner.account.puuid in :puuids
    order by l.league.tier ASC, l.rank ASC, l.leaguePoints ASC
""")
    Page<Summoner> findSortedSummonersByPuuidsUsingTier(
        @Param("puuids") List<String> puuidList, Pageable pageable
    );
}
