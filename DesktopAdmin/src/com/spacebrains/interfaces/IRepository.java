package com.spacebrains.interfaces;

/**
 * Объявляет методы для работы с объектами хранилища
 */
public interface IRepository {
    void put(IDbEntity dbEntity);
    IDbEntity get(int index);
    void delete(int index);
    int size();
}
