package com.gnimtier.riot.data.repository.riot;

import com.gnimtier.riot.data.entity.riot.Account;
import com.gnimtier.riot.data.entity.tft.TFTLeagueEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByPuuid(String puuid);

    Optional<Account> findByGameNameAndTagLine(String gameName, String TagLine);

    Optional<List<Account>> findAllByGameName(String gameName);

    @Query("""
            select a
            from Account a
            left join Summoner s
            on a.puuid = s.puuid
            left join TFTLeagueEntry l
            on a.puuid = l.puuid
            where a.puuid in :puuids
                            order by coalesce(l.rankScore, 0) desc
            """)
    Page<Account> getSortedAccountByTier(@Param("puuids") List<String> puuidList, Pageable pageable);
}
