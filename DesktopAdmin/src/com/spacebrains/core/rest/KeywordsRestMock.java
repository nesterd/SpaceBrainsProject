package com.spacebrains.core.rest;

import com.spacebrains.interfaces.IKeywords;
import com.spacebrains.model.Keyword;
import com.spacebrains.model.Person;

import java.util.ArrayList;

public class KeywordsRestMock implements IKeywords {

    private static KeywordsRestMock instance;

    ArrayList<Keyword> keywords = new ArrayList();
    int nextId = 11;

    public KeywordsRestMock() {
        Person putin = new Person(1, "Путин");
        Person zaharova = new Person(4, "Захарова");
        Person churkin = new Person(7, "Чуркин");
        Person yarovaya = new Person(9, "Яровая");
        Person mironov = new Person(10, "Миронов");

        keywords = new ArrayList<>();
        keywords.add(new Keyword(1, "Путин", putin));
        keywords.add(new Keyword(2, "Путину", putin));
        keywords.add(new Keyword(3, "Путина", putin));
        keywords.add(new Keyword(4, "Захарова", zaharova));
        keywords.add(new Keyword(5, "Захаровой", zaharova));
        keywords.add(new Keyword(6, "Чуркин", churkin));
        keywords.add(new Keyword(7, "Чуркину", churkin));
        keywords.add(new Keyword(8, "Чуркина", churkin));
        keywords.add(new Keyword(9, "Яровая", yarovaya));
        keywords.add(new Keyword(10, "Миронову", mironov));
    }

    @Override
    public ArrayList<Keyword> getKeywords(Person person) {
        if (person == null) return keywords;

        ArrayList<Keyword> personKeywords = new ArrayList<>();
        for (Keyword keyword : keywords) {
            if (keyword.getPerson().equals(person)) personKeywords.add(keyword);
        }
        return personKeywords;
    }

    @Override
    public String save(Keyword keyword) {
        if (keywords.contains(keyword)) {
            keywords.get(keywords.indexOf(keyword))
                    .setName(keyword.getName())
                    .setPerson(keyword.getPerson());
        } else {
            keyword.setID(nextId++);
            keywords.add(keyword);
        }
        return "";
    }

    @Override
    public Keyword get(Keyword keywordToSearch) {
        if (keywords.contains(keywordToSearch)) {
            return keywords.get(keywords.indexOf(keywordToSearch));
        } else return null;
    }

    @Override
    public boolean delete(Keyword keyword) {
        keywords.remove(keyword);
        return true;
    }

    public static KeywordsRestMock getInstance() {
        if (instance == null) instance = new KeywordsRestMock();
        return instance;
    }
}
