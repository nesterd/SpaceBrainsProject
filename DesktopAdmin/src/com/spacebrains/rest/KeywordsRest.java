package com.spacebrains.rest;

import com.spacebrains.interfaces.IKeywords;
import com.spacebrains.model.Keyword;
import com.spacebrains.model.Person;
import com.spacebrains.util.RestParams;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

public class KeywordsRest implements IKeywords {
    private static KeywordsRest instance = null;

    private RestConnector restConnector;
    private ArrayList<Keyword> keywords;

    public KeywordsRest() {
        this.restConnector = new RestConnector();
        this.keywords = new ArrayList<>();
        populateList();
    }

    private void populateList() {
        restConnector.sendGetCommand("/keywords");
        if(restConnector.getState() == RestParams.HTTP_STATE_OK) {
            // populate ArrayList from JSON response
            keywords = new ArrayList<>(restConnector.getJsonObject().entrySet());
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
        if(restConnector.getState() == RestParams.HTTP_STATE_OK) {
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
        if(restConnector.getState() == RestParams.HTTP_STATE_NO_CONTENT) {
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
