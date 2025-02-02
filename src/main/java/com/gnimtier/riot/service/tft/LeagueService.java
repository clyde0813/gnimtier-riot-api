package com.gnimtier.riot.service.tft;

import com.gnimtier.riot.client.RiotKrApiClient;
import com.gnimtier.riot.data.dto.tft.LeagueEntryDto;
import com.gnimtier.riot.data.dto.tft.response.LeagueEntryResponseDto;
import com.gnimtier.riot.data.entity.riot.Summoner;
import com.gnimtier.riot.data.entity.tft.LeagueEntry;
import com.gnimtier.riot.data.repository.riot.SummonerRepository;
import com.gnimtier.riot.data.repository.tft.LeagueEntryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LeagueService {
    private final RiotKrApiClient riotKrApiClient;
    private final LeagueEntryRepository leagueEntryRepository;
    private final SummonerRepository summonerRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    public Map<String, LeagueEntryResponseDto> getByPuuid(String puuid) {
        Summoner summoner = summonerRepository.findByPuuid(puuid).get();
        String summonerId = summoner.getId();
        Map<String, LeagueEntryResponseDto> leagueEntryResponseDtoMap = new HashMap<>();
        List<LeagueEntry> selectedLeagueList = leagueEntryRepository.findAllByPuuid(puuid);
        if (selectedLeagueList.isEmpty()) {
            List<LeagueEntryDto> apiResponseLeagueEntryDtoList = riotKrApiClient.getLeagueEntryBySummonerId(summonerId);
            apiResponseLeagueEntryDtoList.forEach(leagueEntryDto -> {
                LeagueEntry leagueEntry = leagueEntryRepository.save(leagueEntryDto.toEntity(puuid));
                LeagueEntryResponseDto leagueEntryResponseDto = leagueEntry.toDto();
                leagueEntryResponseDtoMap.put(leagueEntry.getQueueType(), leagueEntryResponseDto);
            });
        } else {
            selectedLeagueList.forEach(leagueEntry -> {
                LeagueEntryResponseDto leagueEntryResponseDto = leagueEntry.toDto();
                leagueEntryResponseDtoMap.put(leagueEntry.getQueueType(), leagueEntryResponseDto);
            });
        }
        return leagueEntryResponseDtoMap;
    }
}
