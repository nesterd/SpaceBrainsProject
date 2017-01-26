package com.spacebrains.model;

import com.spacebrains.interfaces.IDbEntity;
import com.spacebrains.interfaces.INamed;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class Keyword implements INamed<Keyword>, Comparable<Person>, JSONAware, IDbEntity {

    private int keywordId;
    private String name;
    private Person person;

    public Keyword(int keywordId, String name) {
        this.keywordId = keywordId;
        this.name = name;
    }

    public Keyword(int keywordId, String name, Person person) {
        this.keywordId = keywordId;
        this.name = name;
        this.person = person;
    }

    public Keyword(String name) {
        this(0, name, new Person("Fake"));
    }

    public Keyword(String name, Person person) {
        this(0, name, person);
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

    @Override
    public String toJSONString() {
        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("personid", person.getID());
        return obj.toJSONString();
    }
}
