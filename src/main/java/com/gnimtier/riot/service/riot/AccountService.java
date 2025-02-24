package com.gnimtier.riot.service.riot;

import com.gnimtier.riot.client.RiotAsiaApiClient;
import com.gnimtier.riot.data.dto.riot.AccountDto;
import com.gnimtier.riot.data.entity.riot.Account;
import com.gnimtier.riot.data.repository.riot.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final RiotAsiaApiClient riotAsiaApiClient;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public Account getAccount(String puuid, boolean refresh) {
        Optional<Account> selectedAccount = accountRepository.findByPuuid(puuid);
        // Account 정보가 없거나 갱신을 요청한 경우
        if (selectedAccount.isEmpty() || refresh) {
            AccountDto apiResponseAccount = riotAsiaApiClient.getAccountByPuuid(puuid);
            LOGGER.info("[AccountService] - getAccount : getting account from api done! - {}", apiResponseAccount.getPuuid());
            return accountRepository.save(apiResponseAccount.toEntity());
        }
        return selectedAccount.get();
    }

    public Account getAccount(String gameName, String tagLine,  boolean refresh) {
        Optional<Account> selectedAccount = accountRepository.findByGameNameAndTagLine(gameName, tagLine);
        // Account 정보가 없거나 갱신을 요청한 경우
        if (selectedAccount.isEmpty() || refresh) {
            AccountDto apiResponseAccount = riotAsiaApiClient.getAccountByGameName(gameName, tagLine);
            LOGGER.info("[AccountService] - getAccount : getting Account from api done! - {}", apiResponseAccount.getPuuid());
            return accountRepository.save(apiResponseAccount.toEntity());
        }
        LOGGER.info("[AccountService] - getAccount : getting Account from DB done! - {}", selectedAccount.get().getPuuid());
        return selectedAccount.get();
    }
}
