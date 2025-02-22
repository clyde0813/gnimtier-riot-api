package com.gnimtier.riot.service.riot;

import com.gnimtier.riot.client.RiotAsiaApiClient;
import com.gnimtier.riot.data.dto.riot.AccountDto;
import com.gnimtier.riot.data.entity.riot.Account;
import com.gnimtier.riot.data.repository.riot.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final RiotAsiaApiClient riotAsiaApiClient;

    @Autowired
    public AccountService(AccountRepository accountRepository, RiotAsiaApiClient riotAsiaApiClient) {
        this.accountRepository = accountRepository;
        this.riotAsiaApiClient = riotAsiaApiClient;
    }


    public Account getByPuuid(String puuid, boolean refresh) {
        Optional<Account> selectedAccount = accountRepository.findByPuuid(puuid);
        if (selectedAccount.isEmpty() || refresh) {
            AccountDto apiResponseAccount = riotAsiaApiClient.getAccountByPuuid(puuid);
            return accountRepository.save(apiResponseAccount.toEntity());
        }
        return selectedAccount.get();
    }

    public Account getByGameNameAndTagLine(String gameName, String tagLine) {
        Optional<Account> selectedAccount = accountRepository.findByGameNameAndTagLine(gameName, tagLine);
        if (selectedAccount.isEmpty()) {
            AccountDto apiResponseAccount = riotAsiaApiClient.getAccountByGameName(gameName, tagLine);
            accountRepository.save(apiResponseAccount.toEntity());
            return accountRepository.save(apiResponseAccount.toEntity());
        }
        return selectedAccount.get();
    }
}
