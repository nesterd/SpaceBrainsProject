package com.spacebrains.model;

import com.spacebrains.core.rest.RESTApiProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SiteRepository {
    private ArrayList<Site> sites;
    private RESTApiProvider rest;

    public SiteRepository() {
        sites = new ArrayList<>();
        rest = new RESTApiProvider();
    }

    /**
     * Сохраняет изменения в списке сайтов путём запроса к REST API
     * @param site - сайт
     * @return true в случае удачного сохранения
     */
    public boolean put(Site site) {
        return rest.updateObject(new SiteDto(site));
    }

    /**
     * Запрашивает список сайтов, обращаясь к REST API
     * @return  ArrayList<Site>
     */
    public ArrayList<Site> get() {
        sites = new ArrayList<>();
        HashMap<Long, String> fromRest = rest.getObjects("sites");
        String value;
        for (long i: fromRest.keySet()) {
            value = fromRest.get(i);
            Site site = new Site((int) i, value);
            sites.add(site);
        }
        return sites;
    }

    /**
     * Удаляет сайт из списка с помощью запроса к REST API
     * @param site - сайт
     * @return true в случае удачного удаления
     */
    public boolean delete(Site site) {
        return rest.deleteObject(new SiteDto(site));
    }

    /**
     * Возвращает количество полученных записей
     * @return int
     */
    public int size() {
        return sites.size();
    }

    public Iterator<Site> iterator() {
        return sites.iterator();
    }

}
