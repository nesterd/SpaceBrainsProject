package com.spacebrains.model;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;

public abstract class DbObject implements JSONAware {
    private HashMap<String, Object> fields;

    public DbObject() {
        fields = new HashMap<String, Object>();
    }

    /**
     * Пример реализации конструктора в субклассах
     * @param jsonString
     *
    public DbObject(String jsonString) {
    fields = new HashMap<String, Object>();
    addProperty("id", 0);
    addProperty("name",null);
    buildFromJSON(jsonString);
    }
     */

    /**
     * Субклассы должны возвращать в этом методе
     * начало строки для запроса в rest
     * @return String
     */
    public abstract String getEntityName();

    public void addProperty(String name, Object value) {
        fields.put(name, value);
    }

    public Object getProperty(String name) {
        return fields.get(name);
    }

    public void setProperty(String key, Object value) {
        fields.replace(key, value);
    }

    public void buildFromJSON(String jsonString) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
            for (String key: fields.keySet()) {
                Object value = jsonObject.get(key);
                setProperty(key, value);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String propertyToJSON(String key) {
        StringBuilder sb = new StringBuilder();
        sb.append("{ \"");
        sb.append(JSONObject.escape(key));
        sb.append("\":\"");
        sb.append(JSONObject.escape(getProperty(key).toString()));
        sb.append("\"}");
        return sb.toString();
    }
}
