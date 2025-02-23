package com.gnimtier.riot.client;

import com.gnimtier.riot.constant.RiotApiConstants;
import com.gnimtier.riot.data.dto.riot.AccountDto;
import com.gnimtier.riot.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RiotAsiaApiClient {
    private final WebClient webClient;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Value("${riot.api.key}")
    String apiKey;


    public AccountDto getAccountByPuuid(String puuid) {
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
//                .onStatus(HttpStatusCode::isSameCodeAs, clientResponse -> Mono.error(new CustomException("Too Many Requests", HttpStatus.BAD_REQUEST)))
//                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new CustomException("Riot API Error", HttpStatus.BAD_REQUEST)))
                .bodyToMono(AccountDto.class)
                .block();
        LOGGER.info("RiotAsiaApiClient - getAccountByPuuid done!");
        return response;
    }

    public AccountDto getAccountByGameName(String gameName, String tagLine) {
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
                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> Mono.error(new CustomException("Not Found", HttpStatus.NOT_FOUND)))
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new CustomException("Too Many Requests", HttpStatus.BAD_REQUEST)))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new CustomException("Riot API Error", HttpStatus.BAD_REQUEST)))
                .bodyToMono(AccountDto.class)
                .block();
        LOGGER.info("RiotAsiaApiClient - getAccountByPuuid done!");
        return response;
    }

}
