package com.spacebrains.model;

import com.spacebrains.core.http.RESTApiProvider;
import com.spacebrains.interfaces.IDbEntity;
import com.spacebrains.interfaces.IStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Скорее всего придётся удалить этот класс, т.к. реализовать работу хранилища с REST через обобщения не удалось
 * @param <T>
 */
public class Storage<T extends IDbEntity> implements IStorage {
    private ArrayList<T> entities;
    private RESTApiProvider rest;

    public Storage() {
        entities = new ArrayList<>();
        rest = new RESTApiProvider();
    }

    @Override
    public boolean put(IDbEntity dbEntity) {
        return false;
    }

    @Override
    public ArrayList<T> get() {
        ArrayList<T> result = new ArrayList<T>();
//        rest.getObjects();  // не работает, изменилась сигнатура метода
        return result;
    }

    @Override
    public ArrayList<T> getByObject(IDbEntity dbEntity) {
        ArrayList<T> result = new ArrayList<T>();
        HashMap<Long, String> fromWeb = rest.getKeywordsByPerson(dbEntity);
        String value;
        for (long i: fromWeb.keySet()) {
            value = fromWeb.get(i);

        }
        return result;
    }

    @Override
    public boolean delete(IDbEntity dbEntity) {
        return false;
    }

    @Override
    public int size() {
        return entities.size();
    }

    public Iterator<T> iterator() {
        return entities.iterator();
    }
}
