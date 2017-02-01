package com.spacebrains.model;

import com.spacebrains.core.rest.RESTApiProvider;

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
        return rest.updateObject(new KeywordDto(keyword));
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

        HashMap<Long, String> fromRest = rest.getKeywordsByPerson(new PersonDto(person));
        String value;
        for (long i: fromRest.keySet()) {
            value = fromRest.get(i);
            KeywordDto keyword = new KeywordDto((int) i, value, person.getID());
            keyword.setPerson(person);
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
        return rest.deleteObject(new KeywordDto(keyword));
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
}
