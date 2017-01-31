package com.spacebrains.interfaces;

import java.util.ArrayList;

/**
 * Объявляет методы для работы с объектами хранилища
 */
public interface IStorage<T extends IDbEntity> {
    boolean put(T dbEntity);
    ArrayList<T> get();
    ArrayList<T> getByObject(T dbEntity);
    boolean delete(T dbEntity);
    int size();
}
