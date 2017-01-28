package com.spacebrains.model;

import com.spacebrains.interfaces.IDbEntity;
import com.spacebrains.interfaces.IRepository;

import java.util.ArrayList;
import java.util.Iterator;

public class Repository implements IRepository {
    private ArrayList<IDbEntity> entities;

    public Repository() {
        entities = new ArrayList<>();
    }

    @Override
    public void put(IDbEntity dbEntity) {
        entities.add(dbEntity);
    }

    @Override
    public IDbEntity get(int index)
    {
        return entities.get(index);
    }

    @Override
    public void delete(int index) {
        entities.remove(index);
    }

    @Override
    public int size() {
        return entities.size();
    }

    public Iterator<IDbEntity> iterator() {
        return entities.iterator();
    }
}
