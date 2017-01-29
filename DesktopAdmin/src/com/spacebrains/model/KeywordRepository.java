package com.spacebrains.model;

import com.spacebrains.core.http.RESTApiProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class KeywordRepository {
    private ArrayList<KeywordDto> keywords = new ArrayList<>();
    private RESTApiProvider rest = new RESTApiProvider();

    public boolean put(KeywordDto keyword) {
        return rest.updateObject(keyword);
    }

    public ArrayList<KeywordDto> get() {
        return keywords;
    }

    public ArrayList<KeywordDto> getByObject(PersonDto person) {
        ArrayList<KeywordDto> result = new ArrayList<KeywordDto>();
        HashMap<Long, String> fromWeb = rest.getKeywordsByPerson(person);
        String value;
        for (long i: fromWeb.keySet()) {
            value = fromWeb.get(i);
            KeywordDto keyword = new KeywordDto((int) i, value, person.getID());
            keyword.setPerson(person);
            result.add(keyword);
        }
        return result;
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
