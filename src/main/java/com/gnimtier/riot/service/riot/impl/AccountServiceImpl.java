package com.gnimtier.riot.service.riot.impl;

import com.gnimtier.riot.client.RiotAsiaApiClient;
import com.gnimtier.riot.data.dto.riot.AccountDto;
import com.gnimtier.riot.data.entity.riot.Account;
import com.gnimtier.riot.data.repository.riot.AccountRepository;
import com.gnimtier.riot.service.riot.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final RiotAsiaApiClient riotAsiaApiClient;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, RiotAsiaApiClient riotAsiaApiClient) {
        this.accountRepository = accountRepository;
        this.riotAsiaApiClient = riotAsiaApiClient;
    }

    public AccountDto entityToDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setPuuid(account.getPuuid());
        accountDto.setGameName(account.getGameName());
        accountDto.setTagLine(account.getTagLine());
        return accountDto;
    }

    private Account dtoToSaveEntity(AccountDto accountDto) {
        Account newAccount = new Account();
        newAccount.setPuuid(accountDto.getPuuid());
        newAccount.setGameName(accountDto.getGameName());
        newAccount.setTagLine(accountDto.getTagLine());
        newAccount.setCreatedDate(LocalDateTime.now());
        return accountRepository.save(newAccount);
    }

    @Override
    public AccountDto getByPuuid(String puuid) {
        Optional<Account> selectedAccount = accountRepository.findByPuuid(puuid);
        if (selectedAccount.isPresent()) {
            return entityToDto(selectedAccount.get());
        } else {
            AccountDto apiResponseAccount = riotAsiaApiClient.getAccountByPuuid(puuid);
            Account account = dtoToSaveEntity(apiResponseAccount);
            return entityToDto(account);
        }
    }

    @Override
    public AccountDto getByGameNameAndTagLine(String gameName, String tagLine) {
        Optional<Account> selectedAccount = accountRepository.findByGameNameAndTagLine(gameName, tagLine);
        if (selectedAccount.isPresent()) {
            return entityToDto(selectedAccount.get());
        } else {
            AccountDto apiResponseAccount = riotAsiaApiClient.getAccountByGameName(gameName, tagLine);
            Account account = dtoToSaveEntity(apiResponseAccount);
            return entityToDto(account);
        }
    }
}
