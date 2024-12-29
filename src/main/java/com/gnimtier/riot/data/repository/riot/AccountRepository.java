package com.gnimtier.riot.data.repository.riot;

import com.gnimtier.riot.data.entity.riot.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {

}
