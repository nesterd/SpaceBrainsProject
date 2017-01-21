package com.spacebrains.interfaces;

import java.util.ArrayList;

public interface IRest<T> {
    String save(T obj);
    T get(T obj);
    ArrayList<T> delete(T obj);
}
