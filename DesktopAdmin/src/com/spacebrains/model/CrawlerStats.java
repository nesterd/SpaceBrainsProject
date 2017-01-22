package com.spacebrains.model;

public class CrawlerStats {
    private Site site;
    private int linksCount;
    private int newLinks;
    private int processedLinks;

    public CrawlerStats(Site site) {
        this(site, 0, 0, 0);
    }

    public CrawlerStats(Site site, int linksCount, int newLinks, int processedLinks) {
        this.site = site;
        this.linksCount = linksCount;
        this.newLinks = newLinks;
        this.processedLinks = processedLinks;
    }

    public Site getSite() {
        return site;
    }

    public int getLinksCount() {
        return linksCount;
    }

    public CrawlerStats setLinksCount(int linksCount) {
        this.linksCount = linksCount;
        return this;
    }

    public int getNewLinks() {
        return newLinks;
    }

    public CrawlerStats setNewLinks(int newLinks) {
        this.newLinks = newLinks;
        return this;
    }

    public int getProcessedLinks() {
        return processedLinks;
    }

    public CrawlerStats setProcessedLinks(int processedLinks) {
        this.processedLinks = processedLinks;
        return this;
    }
}
