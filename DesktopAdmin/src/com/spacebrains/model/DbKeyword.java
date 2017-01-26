package com.spacebrains.model;

public class DbKeyword extends Keyword {
    private int personId;

    public DbKeyword(int keywordId, String name, int personId) {
        super(keywordId, name);
        this.personId = personId;
    }

    public int getPersonId() {
        return  personId;
    }
}
