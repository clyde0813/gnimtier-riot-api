# GNIMTIER Riot API Documentation

##### GNIMTIER Riot API는 그님티.kr 프로젝트를 위한 riot 관련 데이터 처리 전용 서버입니다.

---

### 기본적인 기능

1. riot api 요청을 최소화하기 위한 account, summoner, league등의 데이터 캐싱(DB)
2. 그님티 서비스 기획상 필요한 정렬(SQL)처리
---

Riot Games의 다양한 데이터에 접근할 수 있는 엔드포인트를 제공합니다. 아래는 주요 API 엔드포인트 목록입니다.

## 1. `/tft/summoners/by-gameName-list`

### 설명
gameName, tagLine 을 ArrayList로 POST 요청시 배열에 있는 모든 riot 이용자의 
account, summoner, leagueEntry를 riot api로 부터 받아와 저장합니다.
응답으로는 puuid String List를 반환합니다.

### To DO 
#### 2025.01.12
1. 존재하지 않는 계정 예외처리

### 요청 예시
```json
[
    [
        "San",
        "Chess"
    ],
    [
        "강선종",
        "KR1"
    ]
]
```
### 응답 예시
```json
[
  "u0mnhQDqjLXVvDRj9xLxCOKDTiaaMqXjn2-rungY4fSYNoivJpkI6sgaixwYA9dbge8y32cA6fvDVQ",
  "z1phhKDIySviInHgne9wKgdVWO3v7LS_53y8HcGsdwrN1CPPV7GIV3hn4C5irMsY7p3Irg5ZKwVRKg"
]
```