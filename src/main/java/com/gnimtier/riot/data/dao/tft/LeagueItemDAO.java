package com.gnimtier.riot.data.dao.tft;

import com.gnimtier.riot.data.entity.tft.LeagueItem;

@Deprecated
public interface LeagueItemDAO {
    LeagueItem insertLeagueItem(LeagueItem leagueItem);
    LeagueItem getLeagueItem(String leagueItemId);
    LeagueItem updateLeagueItem(LeagueItem leagueItem);
}
