package com.gnimtier.riot.service.tft;

import ch.qos.logback.classic.util.LogbackMDCAdapter;
import com.gnimtier.riot.client.RiotKrApiClient;
import com.gnimtier.riot.data.dto.tft.LeagueEntryDto;
import com.gnimtier.riot.data.dto.tft.response.LeagueEntryResponseDto;
import com.gnimtier.riot.data.entity.riot.Summoner;
import com.gnimtier.riot.data.entity.tft.LeagueEntry;
import com.gnimtier.riot.data.repository.riot.SummonerRepository;
import com.gnimtier.riot.data.repository.tft.LeagueEntryRepository;
import com.gnimtier.riot.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class LeagueService {
    private final RiotKrApiClient riotKrApiClient;
    private final LeagueEntryRepository leagueEntryRepository;
    private final SummonerRepository summonerRepository;

    @Value("${riot.tft.season}")
    private String season;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public Map<String, LeagueEntryResponseDto> getByPuuid(String puuid, boolean refresh) {
        String summonerId = summonerRepository
                .findByPuuid(puuid)
                .map(Summoner::getId)
                .orElseThrow(() -> new CustomException("Summoner not found", HttpStatus.NOT_FOUND));
        Map<String, LeagueEntryResponseDto> leagueEntryResponseDtoMap = new HashMap<>();
        Optional<LeagueEntry> selectedLeagueEntry = leagueEntryRepository.findById(season + "-" + summonerId + "-" + "RANKED_TFT");
        if (selectedLeagueEntry.isEmpty() || refresh) {
            List<LeagueEntryDto> apiResponseLeagueEntryDtoList = riotKrApiClient.getLeagueEntryBySummonerId(summonerId);

            // RANKED_TFT 만 처리
            apiResponseLeagueEntryDtoList
                    .stream()
                    .filter(dto -> "RANKED_TFT".equals(dto.getQueueType()))
                    .findFirst() // 첫 번째 요소만 처리
                    .ifPresent(leagueEntryDto -> {
                        LeagueEntry leagueEntry = leagueEntryRepository.save(leagueEntryDto.toEntity(puuid, season));
                        LeagueEntryResponseDto leagueEntryResponseDto = leagueEntry.toDto();
                        leagueEntryResponseDtoMap.put(leagueEntry.getQueueType(), leagueEntryResponseDto);
                    });

            // LeagueEntry 전체 저장 삭제
//            apiResponseLeagueEntryDtoList.forEach(leagueEntryDto -> {
//                    LeagueEntry leagueEntry = leagueEntryRepository.save(leagueEntryDto.toEntity(puuid));
//                    LeagueEntryResponseDto leagueEntryResponseDto = leagueEntry.toDto();
//                    leagueEntryResponseDtoMap.put(leagueEntry.getQueueType(), leagueEntryResponseDto);
//            });
        } else {
            selectedLeagueEntry.ifPresent(leagueEntry -> {
                LeagueEntryResponseDto leagueEntryResponseDto = leagueEntry.toDto();
                leagueEntryResponseDtoMap.put(leagueEntry.getQueueType(), leagueEntryResponseDto);
            });
        }
        return leagueEntryResponseDtoMap;
    }
}
