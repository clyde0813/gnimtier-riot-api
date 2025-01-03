package com.gnimtier.riot.data.repository.riot;

import com.gnimtier.riot.data.entity.riot.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByPuuid(String puuid);
    Optional<Account> findByGameNameAndTagLine(String gameName, String TagLine);
    Optional<List<Account>> findAllByGameName(String gameName);
}
