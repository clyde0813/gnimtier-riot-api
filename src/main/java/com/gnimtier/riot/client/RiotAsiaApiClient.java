package com.gnimtier.riot.client;

import com.gnimtier.riot.constant.RiotApiConstants;
import com.gnimtier.riot.data.dto.riot.AccountDto;
import com.gnimtier.riot.data.dto.riot.SummonerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class RiotAsiaApiClient {
    private final WebClient webClient;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${riot.api.key}")
    String apiKey;

    public RiotAsiaApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public AccountDto getAccountByPuuid(String puuid) {
        try {
            AccountDto response = webClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https")
                            .host(RiotApiConstants.ASIA_BASE_URL)
                            .path(RiotApiConstants.ACCOUNTS_BY_PUUID + puuid)
                            .queryParam("api_key", apiKey)
                            .build()
                    )
                    .retrieve()
                    .bodyToMono(AccountDto.class)
                    .block();
            return response;
        } catch (WebClientResponseException e) {
            logger.error("RIOT API (get ACCOUNT) : {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw e;
        }
    }

    public AccountDto getAccountByGameName(String gameName, String tagLine) {
        try {
            AccountDto response = webClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https")
                            .host(RiotApiConstants.ASIA_BASE_URL)
                            .path(RiotApiConstants.ACCOUNTS_BY_RIOT_ID)
                            .queryParam("api_key", apiKey)
                            .build(gameName, tagLine)
                    )
                    .retrieve()
                    .bodyToMono(AccountDto.class)
                    .block();
            return response;
        } catch (WebClientResponseException e) {
            logger.error("RIOT API (get ACCOUNT) : {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw e;
        }
    }

}
