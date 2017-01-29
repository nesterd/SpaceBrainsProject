package com.spacebrains.interfaces;

/**
 * Используется для объявления класса, объекты которого можно разместить в хранилище
 */
public interface IDbEntity {
    public String getEntityTypeString();
    public int getID();
    public void fromJSONString(String jsonString);
    public String toJSONString();
}
