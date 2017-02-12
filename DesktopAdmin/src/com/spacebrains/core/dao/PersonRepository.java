package com.spacebrains.core.dao;

import com.spacebrains.core.rest.RESTApiProvider;
import com.spacebrains.model.Person;

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
        return rest.updateObject(new PersonDao(person));
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
        return rest.deleteObject(new PersonDao(person));
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

    private static class PersonDao extends DbObject {

        PersonDao(String jsonString) {
            super();
            addProperty("id", 0);
            addProperty("name",null);
            buildFromJSONString(jsonString);
        }

        PersonDao(Person person) {
            super();
            addProperty("id", person.getID());
            addProperty("name", person.getName());
        }

        @Override
        public String getEntityName() {
            return "person";
        }
    }
}
