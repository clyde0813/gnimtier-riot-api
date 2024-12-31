package com.gnimtier.riot.service.tft.impl;

import com.gnimtier.riot.data.dto.tft.response.SummonerResponseDto;
import com.gnimtier.riot.service.tft.SummonerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SummonerServiceImpl implements SummonerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    public SummonerServiceImpl(){
//
//    }
    @Override
    public SummonerResponseDto getByGameName(String gameName, String tagLine){

        return new SummonerResponseDto();
    }
}