package com.spacebrains.model;

import java.util.ArrayList;

public class Person {

    private int personId;
    private String name;
    private ArrayList<Keyword> keywords;

    public Person(int personId, String name) {
        this.personId = personId;
        this.name = name;
        clearKeywords();
    }

    public Person(String name) {
        this(0, name);
    }

    public int getPersonId() {
        return personId;
    }

    public Person setPersonId(int personId) {
        this.personId = personId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public ArrayList<Keyword> getKeywords() {
        if (keywords == null) clearKeywords();
        return keywords;
    }

    public Person clearKeywords() {
        this.keywords = new ArrayList<>();
        return this;
    }

    public Person addKeyword(Keyword keyword) {
        this.keywords.add(keyword);
        keyword.setPerson(this);
        return this;
    }
}
