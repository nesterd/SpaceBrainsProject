package com.spacebrains.rest;

import com.spacebrains.interfaces.IStats;
import com.spacebrains.model.CrawlerStats;

import java.util.ArrayList;

public class StatsRestMock implements IStats {

    ArrayList<CrawlerStats> crawlerStats;

    public StatsRestMock() {

    }

    @Override
    public ArrayList<CrawlerStats> getCrawlerStats() {
        return crawlerStats;
    }
}
