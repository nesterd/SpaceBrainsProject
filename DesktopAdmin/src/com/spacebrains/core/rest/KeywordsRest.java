package com.spacebrains.core.rest;

import com.spacebrains.interfaces.IKeywords;
import com.spacebrains.model.Keyword;
import com.spacebrains.model.Person;
import com.spacebrains.core.http.HttpConstants;

import java.util.ArrayList;

public class KeywordsRest implements IKeywords {
    private static KeywordsRest instance = null;

    private RestConnector restConnector = null;
    private ArrayList<Keyword> keywords = null;

    public KeywordsRest() {
        this.restConnector = new RestConnector();
        this.keywords = new ArrayList<>();
        populateList();
    }

    private void populateList() {
        //restConnector.sendGetCommand("/keywords");
        restConnector.sendGetCommand("/id/1/name/Путин");
        if(restConnector.getState() == HttpConstants.HTTP_STATE_OK) {
            // populate ArrayList from JSON response
            if(keywords == null) keywords = new ArrayList<Keyword>();
            // TODO: parse response into ArrayList
        }
    }

    @Override
    public String save(Keyword keyword) {
        populateList();
        int id = 0;
        if (keywords.contains(keyword)) {
            id = keywords.get(keywords.indexOf(keyword)).getID();
        }
        restConnector.sendPutCommand("/keywords/" + id);
        if(restConnector.getState() == HttpConstants.HTTP_STATE_OK) {
            keywords.get(id).setName(keyword.getName()).setPerson(keyword.getPerson());
        }
        else {
            // get ID of newly created row
            Keyword newKeyword = restConnector.getKeywordFromJSON(restConnector.getResultString());
            keyword.setID(newKeyword.getID());
            keywords.add(keyword);
        }
        return Integer.toString(id);
    }

    @Override
    public Keyword get(Keyword keywordToSearch) {
        populateList();
        if (keywords.contains(keywordToSearch)) {
            return keywords.get(keywords.indexOf(keywordToSearch));
        }
        return null;
    }

    @Override
    public boolean delete(Keyword keyword) {
        restConnector.sendDelCommand("/keywords/" + keyword.getID());
        if(restConnector.getState() == HttpConstants.HTTP_STATE_NO_CONTENT) {
            keywords.remove(keyword);
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Keyword> getKeywords(Person person) {
        populateList();
        if (person == null) return keywords;

        ArrayList<Keyword> personKeywords = new ArrayList<>();
        for (Keyword keyword : keywords) {
            if (keyword.getPerson().equals(person)) personKeywords.add(keyword);
        }
        return personKeywords;
    }

    public static KeywordsRest getInstance() {
        if(instance == null) instance = new KeywordsRest();
        return instance;
    }
}
