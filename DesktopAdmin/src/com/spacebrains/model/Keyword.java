package com.spacebrains.model;

import com.spacebrains.interfaces.INamed;

public class Keyword implements INamed, Comparable<Person> {

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

    public int getID() {
        return keywordId;
    }

    public Keyword setID(int keywordId) {
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

    @Override
    public int compareTo(Person o) {
        return getName().compareTo(o.getName());
    }

    @Override
    public String toString() {
        return "Keyword{" +
                "keywordId=" + keywordId +
                ", name='" + name + '\'' +
                "}\n\t" + person;
    }
}
