package com.gnimtier.riot.service.tft;

import com.gnimtier.riot.client.RiotKrApiClient;
import com.gnimtier.riot.data.entity.riot.Account;
import com.gnimtier.riot.data.repository.riot.AccountRepository;
import com.gnimtier.riot.data.repository.riot.SummonerRepository;
import com.gnimtier.riot.exception.CustomException;
import com.gnimtier.riot.service.riot.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("SummonerService 단위 테스트")
class SummonerServiceTest {

    @Mock
    private AccountService accountService;
    @Mock
    private LeagueService leagueService;
    @Mock
    private SummonerRepository summonerRepository;
    @Mock
    private RiotKrApiClient riotKrApiClient;
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private SummonerService summonerService;

//    @Test
//    @DisplayName("getSummonerResponseDto - puuid, refresh:true, valid")
//    void getSummonerResponseDto_withValidPuuidAndRefreshTrue_returnsSummonerResponseDto() {
//        String puuid = "valid-puuid";
//        boolean refresh = true;
//        Account account = new Account();
//        account.setPuuid(puuid);
//        account.setModifiedDate(LocalDateTime
//                .now()
//                .minusHours(3));
//        Summoner summoner = new Summoner();
//        SummonerDto summonerDto = new SummonerDto();
//        summonerDto.setPuuid(puuid);
//        summoner.setPuuid(puuid);
//        Map<String, LeagueEntryResponseDto> leagueEntry = Map.of();
//        SummonerResponseDto summonerResponseDto = new SummonerResponseDto();
//        summonerResponseDto.setPuuid(puuid);
//
//        when(riotKrApiClient.getSummonerByPuuid(puuid)).thenReturn(summonerDto);
//        when(summonerDto.toEntity()).thenReturn(summoner);
//        when(accountRepository.findByPuuid(puuid)).thenReturn(Optional.of(account));
//        when(accountService.getAccount(puuid, refresh)).thenReturn(account);
//        when(summonerRepository.findByPuuid(puuid)).thenReturn(Optional.of(summoner));
//        when(leagueService.getLeagueEntryResponseDto(puuid, refresh)).thenReturn(leagueEntry);
//        when(summonerService.getSummonerResponseDto(puuid, refresh)).thenReturn(summonerResponseDto);
//
//        SummonerResponseDto result = summonerService.getSummonerResponseDto(puuid, refresh);
//
//        assertNotNull(result);
//        assertEquals(puuid, result.getPuuid());
//    }
//
////    @Test
////    void getSummonerResponseDto_withLeagueEntry_returnsSummonerResponseDto() {
////        LeagueEntry leagueEntry = new LeagueEntry();
////        leagueEntry.setPuuid("valid-puuid");
////        Account account = new Account();
////        account.setPuuid("valid-puuid");
////        Summoner summoner = new Summoner();
////        summoner.setPuuid("valid-puuid");
////
////        when(accountService.getAccount(leagueEntry.getPuuid(), false)).thenReturn(account);
////        when(summonerRepository.findByPuuid(leagueEntry.getPuuid())).thenReturn(Optional.of(summoner));
////
////        SummonerResponseDto result = summonerService.getSummonerResponseDto(leagueEntry);
////
////        assertNotNull(result);
////        assertEquals("valid-puuid", result.getPuuid());
////    }
//
//    @Test
//    @DisplayName("getSummonerResponseDto - gameName, tagLine, refresh:true, valid")
//    void getSummonerResponseDto_withValidGameNameAndTagLineAndRefreshTrue_returnsSummonerResponseDto() {
//        String gameName = "valid-gameName";
//        String tagLine = "valid-tagLine";
//        boolean refresh = true;
//        Account account = new Account();
//        account.setPuuid("valid-puuid");
//        Summoner summoner = new Summoner();
//        summoner.setPuuid("valid-puuid");
//        Map<String, LeagueEntryResponseDto> leagueEntry = Map.of();
//
//        when(accountRepository.findByGameNameAndTagLine(gameName, tagLine)).thenReturn(Optional.of(account));
//        when(accountService.getAccount(gameName, tagLine, refresh)).thenReturn(account);
//        when(summonerRepository.findByPuuid(account.getPuuid())).thenReturn(Optional.of(summoner));
//        when(leagueService.getLeagueEntryResponseDto(account.getPuuid(), refresh)).thenReturn(leagueEntry);
//
//        SummonerResponseDto result = summonerService.getSummonerResponseDto(gameName, tagLine, refresh);
//
//        assertNotNull(result);
//        assertEquals("valid-puuid", result.getPuuid());
//    }

    @Test
    void isRecentlyModified_withAccountModifiedRecently_throwsCustomException() {
        Account account = new Account();
        account.setModifiedDate(LocalDateTime.now());

        Optional<Account> optionalAccount = Optional.of(account);

        CustomException exception = assertThrows(CustomException.class, () -> {
            summonerService.isRecentlyModified(optionalAccount);
        });

        assertEquals("Too Many Requests", exception.getMessage());
        assertEquals(HttpStatus.TOO_MANY_REQUESTS, exception.getStatus());
    }

    @Test
    void isRecentlyModified_withAccountNotModifiedRecently_returnsTrue() {
        Account account = new Account();
        account.setModifiedDate(LocalDateTime
                .now()
                .minusMinutes(20));

        Optional<Account> optionalAccount = Optional.of(account);

        boolean result = summonerService.isRecentlyModified(optionalAccount);

        assertTrue(result);
    }
}