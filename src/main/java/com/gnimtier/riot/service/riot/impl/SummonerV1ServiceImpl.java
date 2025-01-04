package com.gnimtier.riot.service.riot.impl;

import com.gnimtier.riot.client.RiotKrApiClient;
import com.gnimtier.riot.data.dto.riot.AccountDto;
import com.gnimtier.riot.data.dto.riot.SummonerDto;
import com.gnimtier.riot.data.dto.tft.response.EntryDto;
import com.gnimtier.riot.data.dto.riot.response.SummonerResponseDto;
import com.gnimtier.riot.data.entity.riot.Summoner;
import com.gnimtier.riot.data.repository.riot.AccountRepository;
import com.gnimtier.riot.data.repository.riot.SummonerRepository;
import com.gnimtier.riot.service.riot.AccountService;
import com.gnimtier.riot.service.riot.SummonerService;
import com.gnimtier.riot.service.tft.impl.LeagueV1ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SummonerV1ServiceImpl implements SummonerService {
    private final AccountService accountService;
    private final LeagueV1ServiceImpl leagueV1Service;
    private final AccountRepository accountRepository;
    private final SummonerRepository summonerRepository;
    private final RiotKrApiClient riotKrApiClient;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SummonerV1ServiceImpl(AccountService accountService, LeagueV1ServiceImpl leagueV1Service, AccountRepository accountRepository, SummonerRepository summonerRepository, RiotKrApiClient riotKrApiClient) {
        this.accountService = accountService;
        this.leagueV1Service = leagueV1Service;
        this.accountRepository = accountRepository;
        this.summonerRepository = summonerRepository;
        this.riotKrApiClient = riotKrApiClient;
    }

    public SummonerDto entityToDto(Summoner summoner) {
        SummonerDto summonerDto = new SummonerDto();
        summonerDto.setAccountId(summoner.getAccountId());
        summonerDto.setProfileIconId(summoner.getProfileIconId());
        summonerDto.setRevisionDate(summoner.getRevisionDate());
        summonerDto.setId(summoner.getId());
        summonerDto.setPuuid(summoner.getAccount().getPuuid());
        summonerDto.setSummonerLevel(summoner.getSummonerLevel());
        return summonerDto;
    }

    public Summoner dtoToEntity(SummonerDto summonerDto) {
        Summoner summoner = new Summoner();
        summoner.setAccountId(summonerDto.getAccountId());
        summoner.setProfileIconId(summonerDto.getProfileIconId());
        summoner.setRevisionDate(summonerDto.getRevisionDate());
        summoner.setId(summonerDto.getId());
        summoner.setSummonerLevel(summonerDto.getSummonerLevel());
        summoner.setAccount(accountRepository.findByPuuid(summonerDto.getPuuid()).get());
        summonerRepository.save(summoner);
        return summoner;
    }


    private SummonerResponseDto getSummonerResponseDto(AccountDto accountDto, SummonerDto summonerDto) {
        SummonerResponseDto responseDto = new SummonerResponseDto();
        responseDto.setPuuid(accountDto.getPuuid());
        responseDto.setGameName(accountDto.getGameName());
        responseDto.setTagLine(accountDto.getTagLine());
        responseDto.setId(summonerDto.getId());
        responseDto.setAccountId(summonerDto.getAccountId());
        responseDto.setProfileIconId(summonerDto.getProfileIconId());
        responseDto.setRevisionDate(summonerDto.getRevisionDate());
        responseDto.setSummonerLevel(summonerDto.getSummonerLevel());
        responseDto.setEntry(leagueV1Service.getBySummonerId(summonerDto.getId()));
        return responseDto;
    }

    @Override
    public SummonerResponseDto getByGameNameAndTagLine(String gameName, String tagLine) {
        AccountDto accountDto = accountService.getByGameNameAndTagLine(gameName, tagLine);
        Optional<Summoner> selectedSummoner = summonerRepository.findByAccountPuuid(accountDto.getPuuid());
        if (selectedSummoner.isEmpty()) {
            SummonerDto apiResponseSummoner = riotKrApiClient.getSummonerByPuuid(accountDto.getPuuid());
            selectedSummoner = Optional.ofNullable(dtoToEntity(apiResponseSummoner));
        }
        SummonerDto summonerDto = entityToDto(selectedSummoner.get());
        return getSummonerResponseDto(accountDto, summonerDto);
    }
}