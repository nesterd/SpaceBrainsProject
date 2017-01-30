package com.spacebrains.model;

import com.spacebrains.core.rest.RESTApiProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class PersonRepository {
    private ArrayList<Person> persons;
    private RESTApiProvider rest;

    public PersonRepository() {
        persons = new ArrayList<>();
        rest = new RESTApiProvider();
    }

    /**
     * Сохраняет изменения в списке личностей путём запроса к REST API
     * @param person - личность
     * @return true в случае удачного сохранения
     */
    public boolean put(Person person) {
        return rest.updateObject(new PersonDto(person));
    }

    /**
     * Запрашивает список личностей, обращаясь к REST API
     * @return  ArrayList<Person>
     */
    public ArrayList<Person> get() {
        persons = new ArrayList<>();
        HashMap<Long, String> fromRest = rest.getObjects("persons");
        String value;
        for (long i: fromRest.keySet()) {
            value = fromRest.get(i);
            Person person = new Person((int) i, value);
            persons.add(person);
        }
        return persons;
    }

    /**
     * Удаляет личность из списка с помощью запроса к REST API
     * @param person - личность
     * @return true в случае удачного удаления
     */
    public boolean delete(Person person) {
        return rest.deleteObject(new PersonDto(person));
    }

    /**
     * Возвращает количество полученных записей
     * @return int
     */
    public int size() {
        return persons.size();
    }

    public Iterator<Person> iterator() {
        return persons.iterator();
    }

}
