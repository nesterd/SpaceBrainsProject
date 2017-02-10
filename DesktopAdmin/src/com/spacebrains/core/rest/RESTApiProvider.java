package com.spacebrains.core.rest;

import com.spacebrains.core.AuthConstants;
import com.spacebrains.core.dao.DbObject;
import com.spacebrains.core.http.HttpProvider;
import org.apache.http.HttpStatus;
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

    private String getPropertyFromJSON(String propertyName) {
        String result = null;
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(httpProvider.getJSONString());
            result = (String) jsonObject.get(propertyName);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void handleError(int status) {
        switch (status) {
            case HttpStatus.SC_NOT_FOUND: {  // Error 404
                throw new RuntimeException(AuthConstants.NOT_FOUND);
            }
            case HttpStatus.SC_UNAUTHORIZED: {  // Error 401
                throw new RuntimeException(AuthConstants.ERR_WRONG_CREDENTIALS);
            }
            case HttpStatus.SC_FORBIDDEN: {   // Error 403
                throw new RuntimeException(AuthConstants.ACCESS_FORBIDDEN);
            }
            case HttpStatus.SC_BAD_REQUEST: {  // Error 400
                throw new RuntimeException(AuthConstants.ERR_WRONG_PWSD);
            }
            case HttpStatus.SC_CREATED: { // Status 201
                break;
            }
            case HttpStatus.SC_OK: {
                break;
            }
            default: {
                throw new RuntimeException(AuthConstants.NOT_ANSWERED);
            }
        }
    }

    /**
     * Запрашивает список объектов справочника
     * @return коллекция объектов в виде пар ключ-значение HashMap
     */
    public <T extends DbObject> HashMap<Long, String> getObjects(String reqString) {
        HashMap<Long, String> result = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        sb.append('/');
        sb.append(reqString);
        int status = httpProvider.doGetMethod(sb.toString());
        handleError(status);
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
     * @param personId объект из хранилища данных, по id которого будет отправлен запрос
     * @return
     */
    public <T extends DbObject> HashMap<Long, String> getKeywordsByPerson(int personId) {
        HashMap<Long, String> result = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        sb.append("/person/");
        sb.append(personId);
        int status = httpProvider.doGetMethod(sb.toString());
        handleError(status);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(httpProvider.getJSONString());
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

    public <T extends DbObject> boolean updateObject(T reqObject) {
        StringBuilder sb = new StringBuilder();
        sb.append('/');
        sb.append(reqObject.getEntityName());
        sb.append('/');
        sb.append(reqObject.getProperty("id"));
        httpProvider.setJSONString(reqObject.toJSONString());
        int status = httpProvider.doPutMethod(sb.toString());
        handleError(status);
        return status == HttpStatus.SC_OK;
    }

    public <T extends DbObject> boolean deleteObject(T reqObject) {
        StringBuilder sb = new StringBuilder();
        sb.append('/');
        sb.append(reqObject.getEntityName());
        sb.append('/');
        sb.append(reqObject.getProperty("id"));
        int status = httpProvider.doDeleteMethod(sb.toString());
        return status == HttpStatus.SC_OK;
    }

    public <T extends DbObject> boolean register(T reqObject) {
        httpProvider.setJSONString(reqObject.toJSONString());
        int status = httpProvider.doPostMethod ("/register");
        handleError(status);
        return status == HttpStatus.SC_CREATED;
    }

    /**
     * Регистрируется в веб-сервисе с заданной парой логин/пароль
     * @param userCredentials
     * @return возвращает JWT ключ, выданный веб-сервисом
     */
    public <T extends DbObject> String login(T userCredentials) {
        String result = null;
        StringBuilder sb = new StringBuilder();
        sb.append('/');
        sb.append(userCredentials.getEntityName());
        httpProvider.setJSONString(userCredentials.toJSONString());
        int status = httpProvider.doPostMethod(sb.toString());
        handleError(status);
        if(status == HttpStatus.SC_OK) {
            result = getPropertyFromJSON("access_token");
            httpProvider.authorize(result);
        }
        return result;
    }

    public <T extends DbObject> boolean changePass (T reqObject) {
        httpProvider.setJSONString(reqObject.propertyToJSON("password"));
        int status = httpProvider.doPutMethod("/user/changepass");
        handleError(status);
        return status == HttpStatus.SC_OK;
    }

    public <T extends DbObject> String getUsers() {
        int status = httpProvider.doGetMethod("/users");
        handleError(status);
        return (status == HttpStatus.SC_OK) ? httpProvider.getJSONString() : null;
    }

    public <T extends DbObject> String getAdmins() {
        int status = httpProvider.doGetMethod("/admins");
        handleError(status);
        return (status == HttpStatus.SC_OK) ? httpProvider.getJSONString() : null;
    }

    public <T extends DbObject> String getStatus() {
        int status = httpProvider.doGetMethod("/status");
        handleError(status);
        return (status == HttpStatus.SC_OK) ? httpProvider.getJSONString() : null;
    }
}
