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
         httpProvider = HttpProvider.getInstance();
    }

    /**
     * Извлекает значение параметра из строки JSON
     * @param parameterName название параметра
     * @return значение параметра в виде строки
     */
    private String getParameterFromJSON(String parameterName) {
        String result = null;
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(httpProvider.getJSONString());
            result = (String) jsonObject.get(parameterName);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Метод для обработки состояний Http-запросов
     * @param status значение статуса выполненного http-запроса
     */
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
            case HttpStatus.SC_OK: {  // Status 200
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

    /**
     * Обновляет запись в базе данных путём вызова метода PUT
     * @param reqObject объект данных, наследник DbObject
     * @return true в случае успешного обновления
     */
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

    /**
     * Удаляет запись из базы данных путём вызова метода DEL
     * @param reqObject объект данных, наследник DbObject
     * @return true в случае успешного удаления
     */
    public <T extends DbObject> boolean deleteObject(T reqObject) {
        StringBuilder sb = new StringBuilder();
        sb.append('/');
        sb.append(reqObject.getEntityName());
        sb.append('/');
        sb.append(reqObject.getProperty("id"));
        int status = httpProvider.doDeleteMethod(sb.toString());
        return status == HttpStatus.SC_OK;
    }

    /**
     * Создает нового пользователя в системе
     * @param reqObject объект с данными о пользователе, наследник DbObject
     * @return true в случае успешной регистрации
     */
    public <T extends DbObject> boolean register(T reqObject) {
        httpProvider.setJSONString(reqObject.toJSONString());
        int status = httpProvider.doPostMethod ("/register");
        handleError(status);
        return status == HttpStatus.SC_CREATED;
    }

    /**
     * Регистрируется в веб-сервисе с заданной парой логин/пароль
     * @param userCredentials объект с регистрационными данными пользователя
     *                        наследник DbObject
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
        if (status == HttpStatus.SC_OK) {
            result = getParameterFromJSON("access_token");
            httpProvider.authorize(result);
        }
        return result;
    }

    /**
     * Выполняет смену пароля
     * @param reqObject объект - наследник DbObject, в параметрах которого указаны
     *                  старый пароль и новый пароль в виде строковых значений
     * @return true в случае успешной смены пароля
     */
    public <T extends DbObject> boolean changePass (T reqObject) {
        httpProvider.setJSONString(reqObject.toJSONString());
        int status = httpProvider.doPutMethod(reqObject.getEntityName());
        handleError(status);
        return status == HttpStatus.SC_OK;
    }

    /**
     * Выполняет сброс пароля для текущего пользователя
     * @param reqObject объект содержит адрес e-mail пользователя
     * @return true в случае успешной отправки сообщения
     */
    public <T extends DbObject> boolean restorePswd (T reqObject) {
        httpProvider.setJSONString(reqObject.toJSONString());
        int status = httpProvider.doPostMethod("/user/restore");
        handleError(status);
        return status == HttpStatus.SC_OK;
    }

    /**
     * Получает список пользователей, администрируемых текущим пользователем
     * В зависимости от роли этот список может содержать администраторов или пользователей
     * либо быть пустым, если текущий пользователь не обладает нужными правами
     * @return список пользователей в виде строки JSON
     */
    public <T extends DbObject> String getUsers() {
        int status = httpProvider.doGetMethod("/users");
        handleError(status);
        return (status == HttpStatus.SC_OK) ? httpProvider.getJSONString() : null;
    }

    /**
     * Получает список параметров текущего пользователя, зарегистрированного в системе
     * @return параметры авторизованного пользователя в виде строки JSON
     */
    public <T extends DbObject> String getStatus() {
        int status = httpProvider.doGetMethod("/status");
        handleError(status);
        return (status == HttpStatus.SC_OK) ? httpProvider.getJSONString() : null;
    }
}
