package com.spacebrains.core.rest;

import com.spacebrains.interfaces.IStats;
import com.spacebrains.model.CrawlerStats;
import com.spacebrains.model.Site;
import com.spacebrains.core.util.Generator;

import java.util.ArrayList;

public class StatsRestMock implements IStats {

    private static StatsRestMock instance;
    ArrayList<CrawlerStats> crawlerStats = new ArrayList();;

    public StatsRestMock() {
        ArrayList<Site> siteList = SitesRestMock.getInstance().getSites();

        for (Site site : siteList) {
            int totalCount = Generator.getRnd().nextInt(29) + 1;
            int newSites = Generator.getRnd().nextInt(totalCount);
            crawlerStats.add(new CrawlerStats(site, totalCount, newSites, totalCount - newSites));
        }
    }

    @Override
    public ArrayList<CrawlerStats> getCrawlerStats() {
        return crawlerStats;
    }

    public static StatsRestMock getInstance() {
        if (instance == null) instance = new StatsRestMock();
        return instance;
    }
}
