package com.spacebrains.model;

import com.spacebrains.core.http.RESTApiProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class KeywordRepository {
    private ArrayList<KeywordDto> keywords;
    private RESTApiProvider rest;
    
    public KeywordRepository() {
    	keywords = new ArrayList<>();
    	rest = new RESTApiProvider();
    }

    public boolean put(KeywordDto keyword) {
        return rest.updateObject(keyword);
    }

    /**
     * Возвращает последний запрошенный список ключевых слов,
     *  так как запрос списка всех ключевых слов без указания личности
     *  не предусмотрен в рамках текущей версии REST API
     * @return  ArrayList<KeywordDto>
     */
    public ArrayList<KeywordDto> get() {
        return keywords;
    }

    public ArrayList<KeywordDto> getByObject(PersonDto person) {
        keywords = new ArrayList<KeywordDto>();
        HashMap<Long, String> fromRest = rest.getKeywordsByPerson(person);
        String value;
        for (long i: fromRest.keySet()) {
            value = fromRest.get(i);
            KeywordDto keyword = new KeywordDto((int) i, value, person.getID());
            keyword.setPerson(person);
            keywords.add(keyword);
        }
        return keywords;
    }

    public boolean delete(KeywordDto keyword) {
        return false;
    }

    public int size() {
        return keywords.size();
    }

    public Iterator<KeywordDto> iterator() {
        return keywords.iterator();
    }
}
