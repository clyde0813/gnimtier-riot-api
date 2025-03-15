package com.gnimtier.riot.service.tft;

import com.gnimtier.riot.client.RiotKrApiClient;
import com.gnimtier.riot.data.dto.tft.LeagueEntryResponseDto;
import com.gnimtier.riot.data.dto.tft.TFTLeagueEntryDto;
import com.gnimtier.riot.data.entity.riot.Summoner;
import com.gnimtier.riot.data.entity.tft.TFTLeagueEntry;
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

@Service
@RequiredArgsConstructor
public class LeagueService {
    private final RiotKrApiClient riotKrApiClient;
    private final LeagueEntryRepository leagueEntryRepository;
    private final SummonerRepository summonerRepository;

    @Value("${riot.tft.season}")
    private String season;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public Map<String, LeagueEntryResponseDto> getLeagueEntryResponseDto(String puuid, boolean refresh) {
        String summonerId = summonerRepository
                .findByPuuid(puuid)
                .map(Summoner::getId)
                .orElseThrow(() -> new CustomException("Summoner not found", HttpStatus.NOT_FOUND));
        Map<String, LeagueEntryResponseDto> leagueEntryResponseDtoMap = new HashMap<>();
        Optional<TFTLeagueEntry> selectedLeagueEntry = leagueEntryRepository.findById(season + "-" + summonerId + "-" + "RANKED_TFT");

        // LeagueEntry가 없거나 갱신 요청이 들어왔을때
        if (selectedLeagueEntry.isEmpty() || refresh) {
            List<TFTLeagueEntryDto> apiResponseLeagueEntryDtoList = riotKrApiClient.getLeagueEntryBySummonerId(summonerId);
            // RANKED_TFT 만 처리
            apiResponseLeagueEntryDtoList
                    .stream()
                    .filter(dto -> "RANKED_TFT".equals(dto.getQueueType()))
                    .findFirst() // 첫 번째 요소만 처리
                    .ifPresent(leagueEntryDto -> {
                        TFTLeagueEntry leagueEntry = leagueEntryRepository.save(leagueEntryDto.toEntity(puuid, season));
                        LeagueEntryResponseDto leagueEntryResponseDto = leagueEntry.toDto();
                        leagueEntryResponseDtoMap.put(leagueEntry.getQueueType(), leagueEntryResponseDto);
                        LOGGER.info("[LeagueService] - getLeagueEntryResponseDto : getting LeagueEntry from api done! - {}", leagueEntry.getPuuid());
                    });
            return leagueEntryResponseDtoMap;

        }
        // 데이터 존재, 갱신 요청 X
        selectedLeagueEntry.ifPresent(leagueEntry -> {
            LeagueEntryResponseDto leagueEntryResponseDto = leagueEntry.toDto();
            leagueEntryResponseDtoMap.put(leagueEntry.getQueueType(), leagueEntryResponseDto);
            LOGGER.info("[LeagueService] - getLeagueEntryResponseDto : getting LeagueEntry from DB done! - {}", leagueEntry.getPuuid());
        });
        return leagueEntryResponseDtoMap;
    }
}
