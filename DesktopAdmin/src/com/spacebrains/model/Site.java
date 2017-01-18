package com.spacebrains.model;

public class Site {

    private int siteId;
    private String name;

    public Site(int siteId, String name) {
        this.siteId = siteId;
        this.name = name;
    }

    public Site(String name) {
        this(0, name);
    }

    public int getSiteId() {
        return siteId;
    }

    public Site setSiteId(int siteId) {
        this.siteId = siteId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Site setName(String name) {
        this.name = name;
        return this;
    }

}
