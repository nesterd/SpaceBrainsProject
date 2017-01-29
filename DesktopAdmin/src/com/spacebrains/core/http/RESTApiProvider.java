package com.spacebrains.core.http;

import com.spacebrains.interfaces.IDbEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Обеспечивает трансляцию запросов приложения в вызовы RESTful API
 * Исходные данные и результаты преобразуются в JSON и обратно
 * и далее передаются в хранилище в виде HashMap
 */
public class RESTApiProvider {

    private HttpProvider httpProvider;

    public RESTApiProvider() {
         httpProvider = new HttpProvider();
    }

    /**
     * Запрашивает список объектов справочника
     * @return
     */
    public <T extends IDbEntity> HashMap<Long, String> getObjects(String reqString) {
        HashMap<Long, String> result = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        sb.append("/");
        sb.append(reqString);
        sb.append("/");
        httpProvider.doGetRequest(sb.toString());
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(httpProvider.getJSONString());
            JSONArray array = (JSONArray) jsonObject.get(reqString);
            Iterator<JSONObject> iterator = array.iterator();
            long key;
            String value;
            while (iterator.hasNext()) {
                jsonObject = iterator.next();
                key = (long) jsonObject.get("id");
                value = (String) jsonObject.get("name");
                result.put(key, value);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Запрашивает у веб-сервиса набор объектов, связанных с указанным
     * @param reqObject объект из хранилища данных, по id которого будет отправлен запрос
     * @return
     */
    public <T extends IDbEntity> HashMap<Long, String> getKeywordsByPerson(T reqObject) {
        HashMap<Long, String> result = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        sb.append("/person/");
        sb.append(reqObject.getID());
        httpProvider.doGetRequest(sb.toString());
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(httpProvider.getJSONString());
//            JSONArray array = (JSONArray) jsonObject.get(reqObject.getEntityTypeString());
            JSONArray array = (JSONArray) jsonObject.get("keywords");
            Iterator<JSONObject> iterator = array.iterator();
            long key;
            String value;
            while (iterator.hasNext()) {
                jsonObject = iterator.next();
                key = (long) jsonObject.get("id");
                value = (String) jsonObject.get("name");
                result.put(key, value);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean updateObject(IDbEntity reqObject) {
        return false;
    }

    public boolean deleteObject(IDbEntity reqObject) {
        return false;
    }
}
