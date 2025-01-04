package com.gnimtier.riot.controller;

import com.gnimtier.riot.controller.riot.AccountController;
import com.gnimtier.riot.data.dto.riot.AccountDto;
import com.gnimtier.riot.service.riot.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {
    private final String puuid = "-QNQEnPe-97RbBrjZZr8vTSvbInXKxZoIA74yy2enMAC7t7DxKvUCuN67YzXtodUeXmg7eKpEJCBgw";
    private final String gameName = "Hide on bush";
    private final String tagLine = "KR1";
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    @Test
    @DisplayName("get Account by Puuid")
    void getAccountByPuuid() throws Exception {
        given(accountService.getByPuuid(puuid)).willReturn(new AccountDto(puuid, gameName, tagLine));

        mockMvc.perform(get("/accounts/by-puuid/{puuid}", puuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameName").value(equalTo(gameName)))
                .andExpect(jsonPath("$.tagLine").value(equalTo(tagLine)))
                .andDo(print());
        verify(accountService).getByPuuid(puuid);
    }

    @Test
    @DisplayName("get Account by gameName&tagLine")
    void getAccountByGameNameAndTagLine() throws Exception {
        given(accountService.getByGameNameAndTagLine(gameName, tagLine)).willReturn(new AccountDto(puuid, gameName, tagLine));

        mockMvc.perform(get("/accounts/by-riot-id/{gameName}/{tagLine}", gameName, tagLine))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameName").value(equalTo(gameName)))
                .andExpect(jsonPath("$.tagLine").value(equalTo(tagLine)))
                .andDo(print());
        verify(accountService).getByGameNameAndTagLine(gameName, tagLine);
    }
}
