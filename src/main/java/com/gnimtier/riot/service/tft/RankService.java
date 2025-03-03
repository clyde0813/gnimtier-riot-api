package com.gnimtier.riot.service.tft;

import com.gnimtier.riot.data.repository.tft.LeagueEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankService {
    private final LeagueEntryRepository leagueEntryRepository;

    public Integer getTierRankByPuuidList(List<String> puuidList, String puuid) {
        return leagueEntryRepository.getTierRankByPuuidList(puuidList, puuid);
    }
}
