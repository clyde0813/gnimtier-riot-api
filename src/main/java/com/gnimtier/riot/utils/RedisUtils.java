package com.gnimtier.riot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnimtier.riot.data.dto.tft.PageableResponseDto;
import com.gnimtier.riot.data.dto.tft.response.SummonerResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtils {
    private final ObjectMapper objectMapper;
    private final StringRedisTemplate stringRedisTemplate;
    @Value("${redis.cache.duration.leaderboard}")
    private int LEADERBOARD_CACHE_DURATION;
    @Value("${redis.cache.duration.ban}")
    private int BAN_CACHE_DURATION;

    private final Logger LOGGER = LoggerFactory.getLogger(RedisUtils.class);

    public void setKeyword(String key) {
        key = "keyword:" + key;
        key = StringUtils.removeWhitespace(key);
        stringRedisTemplate
                .opsForValue()
                .set(key, "NOT_FOUND", BAN_CACHE_DURATION, TimeUnit.HOURS);
        LOGGER.info("[RedisService] - set keyword : key={}, value={}, duration={}", key, "NOT_FOUND", BAN_CACHE_DURATION);
    }

    public Boolean existsByKeyword(String key) {
        key = "keyword:" + key;
        key = StringUtils.removeWhitespace(key);
        Boolean exists = stringRedisTemplate.hasKey(key);
        LOGGER.info("[RedisService] - get keyword : key={}, value={}, exits={}", key, "NOT_FOUND", exists);
        return exists;
    }

    public void setLeaderboard(String key, PageableResponseDto<SummonerResponseDto> leaderboardDto){
        key = "leaderboard:" + key;
        key = StringUtils.removeWhitespace(key);
        key = HashUtils.getSHA256(key);
        try {
            String jsonValue = objectMapper.writeValueAsString(leaderboardDto);
            stringRedisTemplate
                    .opsForValue()
                    .set(key, jsonValue, LEADERBOARD_CACHE_DURATION, TimeUnit.MINUTES);
            LOGGER.info("[RedisService] - set Leaderboard : key={}, value={}, duration={}", key, jsonValue, LEADERBOARD_CACHE_DURATION);
        } catch (JsonProcessingException e) {
            LOGGER.error("[RedisService] - set Leaderboard Serializing Error : key={}", key);
        }
    }

    public PageableResponseDto<SummonerResponseDto> getLeaderboard(String key) {
        key = "leaderboard:" + key;
        key = StringUtils.removeWhitespace(key);
        key = HashUtils.getSHA256(key);
        String jsonValue = stringRedisTemplate
                .opsForValue()
                .get(key);
        if (jsonValue == null) {
            return null;
        }
        try {
            PageableResponseDto<SummonerResponseDto> leaderboardDto = objectMapper.readValue(jsonValue, new TypeReference<PageableResponseDto<SummonerResponseDto>>() {
            });
            LOGGER.info("[RedisService] - get Leaderboard : key={}, value={}", key, jsonValue);
            return leaderboardDto;
        } catch (JsonProcessingException e) {
            LOGGER.error("[RedisService] - get Leaderboard Deserializing Error : key={}", key);
            return null;
        }
    }
}
