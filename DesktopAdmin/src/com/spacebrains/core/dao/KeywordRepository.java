package com.spacebrains.core.dao;

import com.spacebrains.core.rest.RESTApiProvider;
import com.spacebrains.model.Keyword;
import com.spacebrains.model.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class KeywordRepository {
    private ArrayList<Keyword> keywords;
    private RESTApiProvider rest;
    
    public KeywordRepository() {
    	keywords = new ArrayList<>();
    	rest = new RESTApiProvider();
    }

    /**
     * Сохраняет изменения в ключевом слове путём запроса к REST API
     * @param keyword - ключевое слово
     * @return true в случае удачного сохранения
     */
    public boolean put(Keyword keyword) {
        return rest.updateObject(new KeywordDao(keyword));
    }

    /**
     * Возвращает последний запрошенный список ключевых слов,
     *  так как запрос списка всех ключевых слов без указания личности
     *  не предусмотрен в рамках текущей версии REST API
     * @return  ArrayList<Keyword>
     */
    public ArrayList<Keyword> get() {
        return keywords;
    }

    /**
     * Запрашивает список ключевых слов по указанной личности
     * @param person - выбранная личность
     * @return ArrayList<Keyword>
     */
    public ArrayList<Keyword> getByObject(Person person) {
        keywords = new ArrayList<Keyword>();

        if (person == null) return keywords;

        HashMap<Long, String> fromRest = rest.getKeywordsByPerson(person);
        String value;
        for (long i: fromRest.keySet()) {
            value = fromRest.get(i);
            Keyword keyword = new Keyword((int) i, value, person);
            keywords.add(keyword);
        }
        return keywords;
    }

    /**
     * Удаляет ключевое слово из списка с помощью запроса к REST API
     * @param keyword - ключевое слово
     * @return true в случае удачного удаления
     */
    public boolean delete(Keyword keyword) {
        return rest.deleteObject(new KeywordDao(keyword));
    }

    /**
     * Возвращает количество полученных записей
      * @return int
     */
    public int size() {
        return keywords != null ? keywords.size() : 0;
    }

    public Iterator<Keyword> iterator() {
        return keywords.iterator();
    }

    private class KeywordDao extends DbObject {
        KeywordDao(String jsonString) {
            super();
            addProperty("id", 0);
            addProperty("name",null);
            addProperty("person_id", 0);
            buildFromJSON(jsonString);
        }

        KeywordDao(Keyword keyword) {
            super();
            addProperty("id", keyword.getID());
            addProperty("name", keyword.getName());
            addProperty("person_id", keyword.getPerson().getID());
        }

        KeywordDao(int id, String name, int personId) {
            super();
            addProperty("id", id);
            addProperty("name", name);
            addProperty("person_id", personId);
        }

        @Override
        public String getEntityName() {
            return "keyword";
        }

        @Override
        public String toJSONString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{\"");
            for (String key: getProperties()) {
                this.propertyToJSON(key);
            }
            sb.append("}");
            return sb.toString();
        }
    }
}
