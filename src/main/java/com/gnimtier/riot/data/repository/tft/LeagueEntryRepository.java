package com.gnimtier.riot.data.repository.tft;

import com.gnimtier.riot.data.entity.riot.Account;
import com.gnimtier.riot.data.entity.riot.Summoner;
import com.gnimtier.riot.data.entity.tft.TFTLeagueEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LeagueEntryRepository extends JpaRepository<TFTLeagueEntry, Long> {
    Optional<TFTLeagueEntry> findById(String id);

    List<TFTLeagueEntry> findAllByPuuid(String puuid);


    @Query(value = """
                select ranking from (
                select puuid, rank() over (order by rank_score desc) as ranking
                from tft_league_entry
                where puuid in :puuidList
                ) as ranking_table
                where puuid = :puuid
            """, nativeQuery = true)
    Integer getTierRankByPuuidList(List<String> puuidList, String puuid);

    @Deprecated
    @Query("""
                select l
                from TFTLeagueEntry l
                where l.puuid in :puuids
                order by l.rankScore desc
            """)
    Page<TFTLeagueEntry> getSortedLeagueEntryByTier(@Param("puuids") List<String> puuidList, Pageable pageable);
}
