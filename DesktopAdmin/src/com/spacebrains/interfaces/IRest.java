package com.spacebrains.interfaces;

public interface IRest<T> {
    String save(T obj);
    T get(T obj);
    boolean delete(T obj);
}
