package com.spacebrains.model;

import com.spacebrains.interfaces.IKeywordRepository;

import java.util.ArrayList;

public class FakeKeywordRepository implements IKeywordRepository {
    private ArrayList<Keyword> keywords = null;

    public FakeKeywordRepository(ArrayList<Keyword> keywords) {
        this.keywords = keywords;
    }

    public FakeKeywordRepository() {
        if(keywords == null) keywords = new ArrayList<>();

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
        return keywords;
    }
}
