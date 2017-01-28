package com.spacebrains.model;

import com.spacebrains.interfaces.INamed;

public class Site implements INamed<Site>, Comparable<Person> {

    private int siteId;
    private String name;

    public Site(int siteId, String name) {
        this.siteId = siteId;
        this.name = name;
    }

    public Site(String name) {
        this(0, name);
    }

    public int getID() {
        return siteId;
    }

    public Site setID(int siteId) {
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

    @Override
    public int compareTo(Person o) {
        return getName().compareTo(o.getName());
    }

    @Override
    public String toString() {
        return "Site{" +
                "siteId=" + siteId +
                ", name='" + name + '\'' +
                '}';
    }
}
