# 그님티어 라이엇 API 문서

## 소개

그님티어 라이엇 API는 [그님티어.kr](http://그님티어.kr) 프로젝트를 위해 Riot Games의 API 데이터를 효율적으로 처리하고 제공하는 전용 백엔드 서비스입니다. 이 서비스는 Riot API 호출 최소화 및 데이터 캐싱을 통해 성능을 최적화하고, 그님티어 서비스 기획에 필요한 데이터를 제공합니다.

---

## 목차

1.  [기능]
2.  [아키텍처]
    *   [시스템 구성 요소]
    *   [데이터 흐름]
3.  [주요 로직]
    *   [데이터 캐싱 및 갱신]
    *   [랭크 데이터 처리]

---

## 1. 기능

그님티어 라이엇 API 서비스는 다음과 같은 주요 기능을 제공합니다.

*   **데이터 캐싱:** Riot API 호출 비용과 지연 시간을 줄이기 위해 Account, Summoner, LeagueEntry 등의 데이터를 DB 및 Redis에 캐싱합니다.
*   **데이터 갱신:** Riot API 데이터 변경 시점에 맞춰 캐싱된 데이터를 최신 상태로 유지합니다.
*   **데이터 처리:** 그님티어 서비스 기획에 필요한 형태로 데이터를 가공하고 정렬합니다. (예: SQL을 이용한 랭크 순위 처리)
*   **API 제공:** 그님티어 프론트엔드 및 기타 클라이언트가 필요한 Riot 관련 데이터를 제공하는 RESTful API 엔드포인트를 노출합니다.

## 2. 아키텍처

그님티어 라이엇 API 서비스는 다음과 같은 구성 요소로 이루어져 있습니다. (아래는 개념적인 설명이며, 실제 다이어그램을 통해 시각적으로 표현할 수 있습니다.)

### 2.1 시스템 구성 요소

*   **Spring Boot 애플리케이션:** 라이엇 API 호출, 데이터 처리, 캐싱 관리 및 API 엔드포인트 제공을 담당하는 핵심 백엔드 애플리케이션입니다.
*   **Riot API:** Riot Games에서 제공하는 공식 API로, 최신 게임 데이터를 가져오는 데 사용됩니다. (Account-v1, Summoner-v4, TFT League-v1 등)
*   **Redis:** 데이터 갱신 시점 및 빈도를 관리하거나, 자주 요청되는 데이터를 위한 인메모리 캐시로 활용

### 2.2 데이터 흐름

1.  **클라이언트 요청:** 서비스의 엔드포인트에 데이터를 요청합니다.
2.  **서비스 로직:** Spring Boot 애플리케이션의 서비스 레이어에서 요청을 처리합니다.
3.  **캐시 확인:** 요청된 데이터가 Redis에 캐싱되어 있는지 확인합니다.
4.  **캐시 히트:** 데이터가 캐싱되어 있고 유효하다면(TTL), Redis에서 데이터를 가져와 클라이언트에 응답합니다.
5.  **캐시 미스 또는 갱신 필요:** 데이터가 캐싱되어 있지 않다면 DB에서 데이터를 가져와 캐싱+리턴 / 갱신이 필요한 경우, Riot API를 호출합니다.
6.  **Riot API 호출:** Riot API 클라이언트를 통해 Riot Games API 서버에 요청을 보냅니다.
7.  **Riot API 응답:** Riot Games 서버로부터 최신 데이터를 수신합니다.
8.  **데이터 처리 및 캐싱:** 수신된 데이터를 애플리케이션의 데이터 모델에 맞게 처리하고, DB에 저장 또는 갱신합니다.
9.  **응답:** 처리된 데이터를 클라이언트에 응답합니다.

## 3. 주요 로직

### 3.1 데이터 캐싱 및 갱신

`AccountService.java`, `SummonerService.java`, `LeagueService.java`를 중심으로 데이터 캐싱 및 갱신 로직이 구현되어 있습니다.

*   **Account 데이터 캐싱 및 갱신 (`AccountService.java`):**
    *   사용자의 `gameName`과 `tagLine`으로 계정 정보를 요청받으면, 먼저 DB의 `Account` 테이블에서 해당 사용자의 데이터가 있는지 확인합니다.
    *   데이터가 존재하고 일정 시간(예: 1시간) 이내에 갱신되었다면 캐싱된 데이터를 사용합니다.
    *   데이터가 없거나 갱신 시점이 오래되었다면, Riot Asia API (`RiotAsiaApiClient`)를 호출하여 최신 Account 정보를 가져옵니다.
    *   가져온 최신 Account 정보 (`AccountDto`)를 DB의 `Account` 테이블에 저장하거나 기존 레코드를 갱신합니다. 이 과정에서 `puuid`가 중요한 식별자로 사용됩니다.
    *   갱신된 또는 캐싱된 Account 정보를 반환합니다.

*   **LeagueEntry 데이터 캐싱 및 갱신 (`LeagueService.java`):**
    *   사용자의 `puuid`를 기반으로 TFT League Entry 정보를 요청받으면, DB의 `TFTLeagueEntry` 테이블에서 해당 `puuid`를 가진 데이터가 있는지 확인합니다.
    *   데이터가 존재하고 일정 시간(예: 15분) 이내에 갱신되었다면 캐싱된 데이터를 사용합니다.
    *   데이터가 없거나 갱신 시점이 오래되었다면, Riot KR API (`RiotKrApiClient`)를 호출하여 해당 사용자의 TFT League Entry 정보를 가져옵니다.
    *   가져온 최신 League Entry 정보 (`TFTLeagueEntryDto`)를 DB의 `TFTLeagueEntry` 테이블에 저장하거나 기존 레코드를 갱신합니다. League Entry 데이터는 사용자의 현재 랭크, LP, 승/패 기록 등을 포함합니다.
    *   갱신된 또는 캐싱된 League Entry 정보를 반환합니다.

이러한 로직을 통해 Riot API 호출 횟수를 최소화하고, 사용자에게 빠르게 최신 데이터를 제공할 수 있습니다. 갱신 주기는 Riot API의 Rate Limit 및 데이터의 변동성을 고려하여 설정됩니다.

### 3.2 랭크 데이터 처리 (`LeagueService.java`, `RankService.java`)

DB에 저장된 `TFTLeagueEntry` 데이터를 기반으로 다양한 랭크 관련 기능을 제공합니다.

*   **개별 사용자 랭크 정보 제공:** `puuid`를 이용하여 특정 사용자의 현재 TFT 랭크, LP, 승/패 등의 정보를 가져옵니다. 이 데이터는 캐싱 및 갱신 로직을 거친 최신 정보입니다.
*   **랭크 순위 정렬:** 특정 티어/디비전 내에서 LP 기준으로 사용자를 정렬하여 랭킹 정보를 제공합니다. 이 과정은 DB에 저장된 데이터를 SQL 쿼리 등을 사용하여 효율적으로 처리합니다.
