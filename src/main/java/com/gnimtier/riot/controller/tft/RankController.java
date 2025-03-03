package com.gnimtier.riot.controller.tft;

import com.gnimtier.riot.data.dto.gnt.RankRequestDto;
import com.gnimtier.riot.service.tft.RankService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tft/ranks")
@RequiredArgsConstructor
public class RankController {
    private final RankService rankService;

    @PostMapping("/by-puuid-list")
    public ResponseEntity<?> getTierRankByPuuidList(
            @RequestBody RankRequestDto request
            ) {
        return ResponseEntity.ok(rankService.getTierRankByPuuidList(request.getPuuidList(), request.getPuuid()));
    }
}
