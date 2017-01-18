package com.spacebrains.model;

public class Keyword {

    private int keywordId;
    private String name;
    private Person person;

    public Keyword(int keywordId, String name) {
        this.keywordId = keywordId;
        this.name = name;
    }

    public Keyword(String name) {
        this(0, name);
    }

    public int getKeywordId() {
        return keywordId;
    }

    public Keyword setKeywordId(int keywordId) {
        this.keywordId = keywordId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Keyword setName(String name) {
        this.name = name;
        return this;
    }

    public Person getPerson() {
        if (person == null) setPerson(new Person("Fake"));
        return person;
    }

    public Keyword setPerson(Person person) {
        this.person = person;
        return this;
    }
}
