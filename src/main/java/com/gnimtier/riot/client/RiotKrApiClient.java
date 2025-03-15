package com.gnimtier.riot.client;

import com.gnimtier.riot.constant.RiotApiConstants;
import com.gnimtier.riot.constant.SummonerApiConstants;
import com.gnimtier.riot.constant.tft.TftLeagueApiConstants;
import com.gnimtier.riot.data.dto.riot.SummonerDto;
import com.gnimtier.riot.data.dto.tft.TFTLeagueEntryDto;
import com.gnimtier.riot.data.entity.riot.Account;
import com.gnimtier.riot.exception.CustomException;
import com.gnimtier.riot.service.riot.AccountService;
import com.gnimtier.riot.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class RiotKrApiClient {
    private final WebClient webClient;
    private final AccountService accountService;
    private final RedisUtils redisUtils;
    private final Logger LOGGER = LoggerFactory.getLogger(RiotKrApiClient.class);

    @Value("${riot.api.key}")
    String apiKey;

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
                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> {
                    Account account = accountService.getAccount(puuid, false);
                    // Redis에 검색 결과 없음 상태 저장
                    redisUtils.setKeyword(account.getGameName() + "#" + account.getTagLine());
                    redisUtils.setKeyword(puuid);
                    // 예외 반환
                    return Mono.error(new CustomException("Not Found", HttpStatus.NOT_FOUND));
                })
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new CustomException("Bad Request", HttpStatus.BAD_REQUEST)))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new CustomException("Riot API Error", HttpStatus.BAD_REQUEST)))
                .bodyToMono(SummonerDto.class)
                .block();
        LOGGER.info("RiotKrApiClient - getSummonerByPuuid done!");
        return response;
    }

    public List<TFTLeagueEntryDto> getLeagueEntryBySummonerId(String summonerId) {
        List<TFTLeagueEntryDto> response = webClient
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
                .bodyToMono(new ParameterizedTypeReference<List<TFTLeagueEntryDto>>() {
                })
                .block();
        LOGGER.info("RiotKrApiClient - getLeagueEntryBySummonerId done!");
        return response;
    }
}
