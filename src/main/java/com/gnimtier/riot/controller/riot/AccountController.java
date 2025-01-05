package com.gnimtier.riot.controller.riot;

import com.gnimtier.riot.data.dto.riot.AccountDto;
import com.gnimtier.riot.data.entity.riot.Account;
import com.gnimtier.riot.service.riot.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Deprecated
@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/by-puuid/{puuid}")
    public AccountDto getByPuuid(@PathVariable("puuid") String puuid) {
        return accountService.getByPuuid(puuid);
    }

    @GetMapping("/by-riot-id/{gameName}/{tagLine}")
    public AccountDto getByGameNameAndTagLine(@PathVariable("gameName") String gameName, @PathVariable("tagLine") String tagLine) {
        return accountService.getByGameNameAndTagLine(gameName, tagLine);
    }
}
