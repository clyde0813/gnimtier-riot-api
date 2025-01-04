package com.gnimtier.riot.client;

import com.gnimtier.riot.constant.RiotApiConstants;
import com.gnimtier.riot.constant.SummonerApiConstants;
import com.gnimtier.riot.constant.tft.TftLeagueApiConstants;
import com.gnimtier.riot.data.dto.riot.SummonerDto;
import com.gnimtier.riot.data.dto.tft.LeagueEntryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Component
public class RiotKrApiClient {
    private final WebClient webClient;
    private final Logger logger = LoggerFactory.getLogger(RiotKrApiClient.class);

    @Value("${riot.api.key}")
    String apiKey;

    public RiotKrApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    private void logError(String where, HttpStatusCode status, String message) {
        logger.error("{}: {}: {}", where, status, message);
    }

    private void logInfo(String message) {
        logger.info("{}", message);
    }

    public SummonerDto getSummonerByPuuid(String puuid) {
        try {
            SummonerDto response = webClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https")
                            .host(RiotApiConstants.KR_BASE_URL)
                            .path(SummonerApiConstants.TFT_SUMMONER_BY_PUUID + puuid)
                            .queryParam("api_key", apiKey)
                            .build()
                    )
                    .retrieve()
                    .bodyToMono(SummonerDto.class)
                    .block();
            logInfo("RiotKrApiClient - getSummonerByPuuid done!");
            return response;
        } catch (WebClientResponseException e) {
            logError("RiotKrApiClient - getSummonerByPuuid", e.getStatusCode(), e.getMessage());
            throw e;
        }
    }

    public List<LeagueEntryDto> getLeagueEntryBySummonerId(String summonerId) {
        try {
            List<LeagueEntryDto> response = webClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https")
                            .host(RiotApiConstants.KR_BASE_URL)
                            .path(TftLeagueApiConstants.TFT_LEAGUE_ENTRY_BY_SUMMONER_ID + summonerId)
                            .queryParam("api_key", apiKey)
                            .build()
                    )
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<LeagueEntryDto>>() {})
                    .block();
            logInfo("RiotKrApiClient - getLeagueEntryBySummonerId done!");
            return response;
        } catch (WebClientResponseException e) {
            logError("RiotKrApiClient - getLeagueEntryBySummonerId", e.getStatusCode(), e.getMessage());
            throw e;
        }
    }
}
