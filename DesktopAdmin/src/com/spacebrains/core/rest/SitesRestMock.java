package com.spacebrains.core.rest;

import com.spacebrains.interfaces.ISites;
import com.spacebrains.model.Site;

import java.util.ArrayList;

public class SitesRestMock implements ISites {

    private static SitesRestMock instance;

    ArrayList<Site> sites = new ArrayList();
    int nextId = 6;

    public SitesRestMock() {
        sites = new ArrayList<>();
        sites.add(new Site(1, "lenta.ru"));
        sites.add(new Site(2, "yandex.ru"));
        sites.add(new Site(3, "goodle.ru"));
        sites.add(new Site(4, "mail.ru"));
        sites.add(new Site(5, "vz.ru"));
    }

    @Override
    public ArrayList<Site> getSites() {
        return sites;
    }

    @Override
    public String save(Site site) {
        if (sites.contains(site)) {
            sites.get(sites.indexOf(site)).setName(site.getName());
        } else {
            site.setID(nextId++);
            sites.add(site);
        }
        return "";
    }

    @Override
    public Site get(Site siteToSearch) {
        if (sites.contains(siteToSearch)) {
            return sites.get(sites.indexOf(siteToSearch));
        } else return null;
    }

    @Override
    public boolean delete(Site site) {
        sites.remove(site);
        return true;
    }

    public static SitesRestMock getInstance() {
        if (instance == null) instance = new SitesRestMock();
        return instance;
    }
}
