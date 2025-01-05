package com.gnimtier.riot.service.tft.impl;

import com.gnimtier.riot.client.RiotKrApiClient;
import com.gnimtier.riot.data.dto.tft.LeagueEntryDto;
import com.gnimtier.riot.data.dto.tft.response.LeagueEntryResponseDto;
import com.gnimtier.riot.data.entity.riot.Summoner;
import com.gnimtier.riot.data.entity.tft.League;
import com.gnimtier.riot.data.entity.tft.LeagueEntry;
import com.gnimtier.riot.data.entity.tft.QueueType;
import com.gnimtier.riot.data.repository.riot.SummonerRepository;
import com.gnimtier.riot.data.repository.tft.LeagueEntryRepository;
import com.gnimtier.riot.data.repository.tft.LeagueRepository;
import com.gnimtier.riot.data.repository.tft.QueueTypeRepository;
import com.gnimtier.riot.service.tft.LeagueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LeagueV1ServiceImpl implements LeagueService {
    private final RiotKrApiClient riotKrApiClient;
    private final SummonerRepository summonerRepository;
    private final LeagueRepository leagueRepository;
    private final QueueTypeRepository queueTypeRepository;
    private final LeagueEntryRepository leagueEntryRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public LeagueV1ServiceImpl(SummonerRepository summonerRepository, LeagueEntryRepository leagueItemRepository, RiotKrApiClient riotKrApiClient, LeagueRepository leagueRepository, QueueTypeRepository queueTypeRepository, LeagueEntryRepository leagueEntryRepository) {
        this.riotKrApiClient = riotKrApiClient;
        this.summonerRepository = summonerRepository;
        this.leagueRepository = leagueRepository;
        this.queueTypeRepository = queueTypeRepository;
        this.leagueEntryRepository = leagueEntryRepository;
    }

    private int tierToInt(String tier) {
        return switch (tier) {
            case "CHALLENGER" -> 1;
            case "GRANDMASTER" -> 2;
            case "MASTER" -> 3;
            case "DIAMOND" -> 4;
            case "EMERALD" -> 5;
            case "PLATINUM" -> 6;
            case "GOLD" -> 7;
            case "SILVER" -> 8;
            case "BRONZE" -> 9;
            case "IRON" -> 10;
            case null, default -> 0;
        };
    }

    private int romanToInt(String roman) {
        if (Objects.equals(roman, "I")) {
            return 1;
        } else if (Objects.equals(roman, "II")) {
            return 2;
        } else if (Objects.equals(roman, "III")) {
            return 3;
        } else if (Objects.equals(roman, "IV")) {
            return 4;
        } else if (Objects.equals(roman, "V")) {
            return 5;
        }
        return 0;
    }

    private LeagueEntry dtoToEntity(LeagueEntryDto leagueEntryDto) {
        Optional<QueueType> queueType = queueTypeRepository.findByName(leagueEntryDto.getQueueType());
        Optional<League> league = leagueRepository.findById(leagueEntryDto.getLeagueId());
        Optional<Summoner> summoner = summonerRepository.findById(leagueEntryDto.getSummonerId());
        if (queueType.isEmpty()) {
            QueueType newQueueType = new QueueType();
            newQueueType.setName(leagueEntryDto.getQueueType());
            queueType = Optional.of(queueTypeRepository.save(newQueueType));
        }
        if (league.isEmpty()) {
            League newLeague = new League();
            newLeague.setId(leagueEntryDto.getLeagueId());
            newLeague.setTier(tierToInt(leagueEntryDto.getTier()));
            newLeague.setQueueType(queueType.get());
            league = Optional.of(leagueRepository.save(newLeague));
        }
        LeagueEntry newLeagueEntry = new LeagueEntry();
        newLeagueEntry.setLeague(league.get());
//        newLeagueEntry.setTier(tier.get());
        newLeagueEntry.setRank(romanToInt(leagueEntryDto.getRank()));
        newLeagueEntry.setSummoner(summoner.get());
        newLeagueEntry.setLeaguePoints(leagueEntryDto.getLeaguePoints());
        newLeagueEntry.setWins(leagueEntryDto.getWins());
        newLeagueEntry.setLosses(leagueEntryDto.getLosses());
        newLeagueEntry.setVeteran(leagueEntryDto.getVeteran());
        newLeagueEntry.setFreshBlood(leagueEntryDto.getFreshBlood());
        newLeagueEntry.setHotStreak(leagueEntryDto.getHotStreak());
        return leagueEntryRepository.save(newLeagueEntry);
    }

    private LeagueEntryResponseDto entityToDto(LeagueEntry leagueEntry) {
        LeagueEntryResponseDto responseDto = new LeagueEntryResponseDto();
        responseDto.setLeaguePoints(leagueEntry.getLeaguePoints());
        responseDto.setRank(leagueEntry.getRank());
        responseDto.setWins(leagueEntry.getWins());
        responseDto.setLosses(leagueEntry.getLosses());
        responseDto.setVeteran(leagueEntry.getVeteran());
        responseDto.setFreshBlood(leagueEntry.getFreshBlood());
        responseDto.setHotStreak(leagueEntry.getHotStreak());
        responseDto.setTier(leagueEntry.getLeague().getTier());
        responseDto.setLeagueId(leagueEntry.getLeague().getId());
        responseDto.setQueueType(leagueEntry.getLeague().getQueueType().getName());
        return responseDto;
    }

    @Override
    public Map<String, LeagueEntryResponseDto> getBySummonerId(String summonerId) {
        Map<String, LeagueEntryResponseDto> leagueEntryResponseDtoMap = new HashMap<>();
        List<LeagueEntry> selectedLeagueList = leagueEntryRepository.findAllBySummonerId(summonerId);
        if (selectedLeagueList.isEmpty()) {
            List<LeagueEntryDto> apiResponseLeagueEntryDtoList = riotKrApiClient.getLeagueEntryBySummonerId(summonerId);
            apiResponseLeagueEntryDtoList.forEach(leagueEntryDto -> {
                LeagueEntryResponseDto leagueEntryResponseDto = entityToDto(dtoToEntity(leagueEntryDto));
                leagueEntryResponseDtoMap.put(leagueEntryResponseDto.getQueueType(), leagueEntryResponseDto);
            });
        } else {
            selectedLeagueList.forEach(leagueEntry -> {
                LeagueEntryResponseDto leagueEntryResponseDto = entityToDto(leagueEntry);
                leagueEntryResponseDtoMap.put(leagueEntryResponseDto.getQueueType(), leagueEntryResponseDto);
            });
        }
        return leagueEntryResponseDtoMap;
    }
}
