package com.spacebrains.core.rest;

import com.spacebrains.interfaces.IPersons;
import com.spacebrains.model.Person;

import java.util.ArrayList;

public class PersonsRestMock implements IPersons {

    private static PersonsRestMock instance;

    ArrayList<Person> persons = new ArrayList();
    int nextId = 11;

    public PersonsRestMock() {
        persons = new ArrayList<>();
        //persons.add(new Person(1, "Путин"));
        persons.add(new Person(1, "Путин"));
        persons.add(new Person(2, "Медведев"));
        persons.add(new Person(3, "Шойгу"));
//        persons.add(new Person(4, "Захарова"));
//        persons.add(new Person(5, "Жириновский"));
//        persons.add(new Person(6, "Зюганов"));
//        persons.add(new Person(7, "Чуркин"));
//        persons.add(new Person(8, "Рогозин"));
//        persons.add(new Person(9, "Яровая"));
//        persons.add(new Person(10, "Миронов"));
    }

    @Override
    public ArrayList<Person> getPersons() {
        return persons;
    }

    @Override
    public String save(Person person) {
        if (persons.contains(person)) {
            persons.get(persons.indexOf(person)).setName(person.getName());
        } else {
            person.setID(nextId++);
            persons.add(person);
        }
        return "";
    }

    @Override
    public Person get(Person personToSearch) {
        if (persons.contains(personToSearch)) {
            return persons.get(persons.indexOf(personToSearch));
        } else return null;
    }

    @Override
    public boolean delete(Person person) {
        persons.remove(person);
        return true;
    }

    public static PersonsRestMock getInstance() {
        if (instance == null) instance = new PersonsRestMock();
        return instance;
    }
}
