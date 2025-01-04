package com.gnimtier.riot.controller;

import com.gnimtier.riot.controller.riot.SummonerV1Controller;
import com.gnimtier.riot.data.dto.tft.response.EntryDto;
import com.gnimtier.riot.data.dto.riot.response.SummonerResponseDto;
import com.gnimtier.riot.service.riot.SummonerService;
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


@WebMvcTest(controllers = SummonerV1Controller.class)
public class SummonerV1ControllerTest {
    private final String puuid = "-QNQEnPe-97RbBrjZZr8vTSvbInXKxZoIA74yy2enMAC7t7DxKvUCuN67YzXtodUeXmg7eKpEJCBgw";
    private final String gameName = "Hide on bush";
    private final String tagLine = "KR1";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SummonerService summonerService;

    private SummonerResponseDto createMockSummonerResponse() {
        return SummonerResponseDto.builder()
                .puuid(puuid)
                .gameName(gameName)
                .tagLine(tagLine)
                .id("2i8b1a193yNxVdlQRQbDEfG0zUHglCNfA3hRMoN22c4ovA")
                .accountId("iCINQ9xJHXBQ4aYcERSh5OrqBmBm3uh1WxKxt2bw_YOq")
                .profileIconId(6)
                .revisionDate(1735925676796L)
                .summonerLevel(788L)
                .entry(EntryDto.builder().RANKED_ENTRY(null).build())
                .build();
    }

    @Test
    @DisplayName("get SummonerResponse by gameName&tagLine")
    void getSummonerResponseByGameNameAndTagLine() throws Exception {
        SummonerResponseDto mockSummonerResponse = createMockSummonerResponse();
        given(summonerService.getByGameNameAndTagLine(gameName, tagLine)).willReturn(mockSummonerResponse);
        mockMvc.perform(get("/tft/summoner/v1/summoners/by-puuid/by-riot-id/{gameName}/{tagLine}", gameName, tagLine))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameName", equalTo(gameName)))
                .andExpect(jsonPath("$.tagLine", equalTo(tagLine)))
                .andExpect(jsonPath("$.puuid", equalTo(puuid)))
                .andDo(print());
        verify(summonerService).getByGameNameAndTagLine(gameName, tagLine);
    }
}
