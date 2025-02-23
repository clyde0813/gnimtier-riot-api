package com.gnimtier.riot.client;

import com.gnimtier.riot.constant.RiotApiConstants;
import com.gnimtier.riot.constant.SummonerApiConstants;
import com.gnimtier.riot.constant.tft.TftLeagueApiConstants;
import com.gnimtier.riot.data.dto.riot.SummonerDto;
import com.gnimtier.riot.data.dto.tft.LeagueEntryDto;
import com.gnimtier.riot.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class RiotKrApiClient {
    private final WebClient webClient;
    private final Logger LOGGER = LoggerFactory.getLogger(RiotKrApiClient.class);

    @Value("${riot.api.key}")
    String apiKey;

    public RiotKrApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public SummonerDto getSummonerByPuuid(String puuid) {
        SummonerDto response = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host(RiotApiConstants.KR_BASE_URL)
                        .path(SummonerApiConstants.TFT_SUMMONER_BY_PUUID + puuid)
                        .queryParam("api_key", apiKey)
                        .build())
                .retrieve()
                .onStatus(HttpStatus.TOO_MANY_REQUESTS::equals, clientResponse -> Mono.error(new CustomException("Too Many Requests", HttpStatus.BAD_REQUEST)))
                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> Mono.error(new CustomException("Not Found", HttpStatus.NOT_FOUND)))
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new CustomException("Bad Request", HttpStatus.BAD_REQUEST)))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new CustomException("Riot API Error", HttpStatus.BAD_REQUEST)))
                .bodyToMono(SummonerDto.class)
                .block();
        LOGGER.info("RiotKrApiClient - getSummonerByPuuid done!");
        return response;
    }

    public List<LeagueEntryDto> getLeagueEntryBySummonerId(String summonerId) {
        List<LeagueEntryDto> response = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host(RiotApiConstants.KR_BASE_URL)
                        .path(TftLeagueApiConstants.TFT_LEAGUE_ENTRY_BY_SUMMONER_ID + summonerId)
                        .queryParam("api_key", apiKey)
                        .build())
                .retrieve()
                .onStatus(HttpStatus.TOO_MANY_REQUESTS::equals, clientResponse -> Mono.error(new CustomException("Too Many Requests", HttpStatus.BAD_REQUEST)))
                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> Mono.error(new CustomException("Not Found", HttpStatus.NOT_FOUND)))
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new CustomException("Bad Request", HttpStatus.BAD_REQUEST)))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new CustomException("Riot API Error", HttpStatus.BAD_REQUEST)))
                .bodyToMono(new ParameterizedTypeReference<List<LeagueEntryDto>>() {
                })
                .block();
        LOGGER.info("RiotKrApiClient - getLeagueEntryBySummonerId done!");
        return response;
    }
}
