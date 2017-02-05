package com.spacebrains.model;

import com.spacebrains.interfaces.INamed;

/**
 * @author Tatyana Vorobeva
 */
public class Person implements INamed<Person>, Comparable<Person> {

    private int personId;
    private String name;

    public Person(int personId, String name) {
        this.personId = personId;
        this.name = name;
    }

    public Person(String name) {
        this(0, name);
    }

    public int getID() {
        return personId;
    }

    public Person setID(int personId) {
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

    @Override
    public int compareTo(Person o) {
        return getName().compareTo(o.getName());
    }

    @Override
    public String toString() {
        return "Person: {" +
                "personId=" + personId +
                ", name='" + name + "\'}";
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && ((Person) obj).getID() == this.getID();
    }
}
