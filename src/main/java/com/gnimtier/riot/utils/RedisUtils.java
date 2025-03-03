package com.gnimtier.riot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnimtier.riot.data.dto.gnt.PageableResponseDto;
import com.gnimtier.riot.data.dto.tft.SummonerResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtils {
    private final ObjectMapper objectMapper;
    private final StringRedisTemplate stringRedisTemplate;
//    @Value("${redis.cache.duration.summoner}")
//    private int SUMMONER_CACHE_DURATION;
    @Value("${redis.cache.duration.leaderboard}")
    private int LEADERBOARD_CACHE_DURATION;
    @Value("${redis.cache.duration.ban}")
    private int BAN_CACHE_DURATION;

    private final Logger LOGGER = LoggerFactory.getLogger(RedisUtils.class);

    public void setKeyword(String key) {
        key = StringUtils
                .removeWhitespace(key)
                .toLowerCase();
        key = HashUtils.getSHA256(key);
        key = "keyword:" + key;
        stringRedisTemplate
                .opsForValue()
                .set(key, "NOT_FOUND", BAN_CACHE_DURATION, TimeUnit.HOURS);
        LOGGER.info("[RedisService] - set keyword : key={}, value={}, duration={}", key, "NOT_FOUND", BAN_CACHE_DURATION);
    }

    public Boolean existsByKeyword(String key) {
        key = StringUtils
                .removeWhitespace(key)
                .toLowerCase();
        key = HashUtils.getSHA256(key);
        key = "keyword:" + key;
        Boolean exists = stringRedisTemplate.hasKey(key);
        LOGGER.info("[RedisService] - get keyword : key={}, value={}, exits={}", key, "NOT_FOUND", exists);
        return exists;
    }

    public void setSummoner(String key, SummonerResponseDto summonerResponseDto) {
        key = StringUtils
                .removeWhitespace(key)
                .toLowerCase();
        key = HashUtils.getSHA256(key);
        key = "summoner:" + key;
        try {
            String jsonValue = objectMapper.writeValueAsString(summonerResponseDto);
            stringRedisTemplate
                    .opsForValue()
                    .set(key, jsonValue);
            LOGGER.info("[RedisService] - set Summoner : key={}, duration={}", key);
        } catch (JsonProcessingException e) {
            LOGGER.error("[RedisService] - set Summoner Serializing Error : key={}", key);
        }
    }

    public SummonerResponseDto getSummoner(String key) {
        key = StringUtils
                .removeWhitespace(key)
                .toLowerCase();
        key = HashUtils.getSHA256(key);
        key = "summoner:" + key;
        String jsonValue = stringRedisTemplate
                .opsForValue()
                .get(key);
        if (jsonValue == null) {
            return null;
        }
        try {
            SummonerResponseDto summonerResponseDto = objectMapper.readValue(jsonValue, SummonerResponseDto.class);
            LOGGER.info("[RedisService] - get Summoner : key={}", key);
            return summonerResponseDto;
        } catch (JsonProcessingException e) {
            LOGGER.error("[RedisService] - get Summoner Deserializing Error : key={}", key);
            return null;
        }
    }

    public void deleteSummoner(String key) {
        key = StringUtils
                .removeWhitespace(key)
                .toLowerCase();
        key = HashUtils.getSHA256(key);
        key = "summoner:" + key;
        stringRedisTemplate.delete(key);
        LOGGER.info("[RedisService] - delete Summoner : key={}", key);
    }

    public void setLeaderboard(String key, PageableResponseDto<SummonerResponseDto> leaderboardDto) {
        key = StringUtils.removeWhitespace(key);
        key = HashUtils.getSHA256(key);
        key = "leaderboard:" + key;
        try {
            String jsonValue = objectMapper.writeValueAsString(leaderboardDto);
            stringRedisTemplate
                    .opsForValue()
                    .set(key, jsonValue, LEADERBOARD_CACHE_DURATION, TimeUnit.MINUTES);
            LOGGER.info("[RedisService] - set Leaderboard : key={}, duration={}", key, LEADERBOARD_CACHE_DURATION);
        } catch (JsonProcessingException e) {
            LOGGER.error("[RedisService] - set Leaderboard Serializing Error : key={}", key);
        }
    }

    public PageableResponseDto<SummonerResponseDto> getLeaderboard(String key) {
        key = StringUtils.removeWhitespace(key);
        key = HashUtils.getSHA256(key);
        key = "leaderboard:" + key;
        String jsonValue = stringRedisTemplate
                .opsForValue()
                .get(key);
        if (jsonValue == null) {
            return null;
        }
        try {
            PageableResponseDto<SummonerResponseDto> leaderboardDto = objectMapper.readValue(jsonValue, new TypeReference<PageableResponseDto<SummonerResponseDto>>() {
            });
            LOGGER.info("[RedisService] - get Leaderboard : key={}", key);
            return leaderboardDto;
        } catch (JsonProcessingException e) {
            LOGGER.error("[RedisService] - get Leaderboard Deserializing Error : key={}", key);
            return null;
        }
    }
}
