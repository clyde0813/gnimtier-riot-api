package com.gnimtier.riot.service.riot.impl;

import com.gnimtier.riot.client.RiotAsiaApiClient;
import com.gnimtier.riot.data.dto.riot.AccountDto;
import com.gnimtier.riot.data.entity.riot.Account;
import com.gnimtier.riot.data.repository.riot.AccountRepository;
import com.gnimtier.riot.service.riot.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public AccountDto getByPuuid(String puuid){
        Optional<Account> selectedAccount = accountRepository.findByPuuid(puuid);
        if(selectedAccount.isPresent()){
            AccountDto accountDto = new AccountDto();
            accountDto.setPuuid(selectedAccount.get().getPuuid());
            accountDto.setGameName(selectedAccount.get().getGameName());
            accountDto.setTagLine(selectedAccount.get().getTagLine());
            return accountDto;
        } else {
            AccountDto apiResponseAccount =  riotAsiaApiClient.getAccountByPuuid(puuid);
            Account newAccount = new Account();
            newAccount.setPuuid(puuid);
            newAccount.setGameName(apiResponseAccount.getGameName());
            newAccount.setTagLine(apiResponseAccount.getTagLine());
            accountRepository.save(newAccount);
            AccountDto accountDto = new AccountDto();
            accountDto.setPuuid(puuid);
            accountDto.setGameName(apiResponseAccount.getGameName());
            accountDto.setTagLine(apiResponseAccount.getTagLine());
            return accountDto;
        }
    }

//    @Override
//    public getByGameNameAndTagLine(String gameName, String tagLine){
//
//    }
}
