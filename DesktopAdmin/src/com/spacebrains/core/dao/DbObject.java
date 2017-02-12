package com.spacebrains.core.dao;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

public abstract class DbObject implements JSONAware {
    private LinkedHashMap<String, Object> fields;

    public DbObject() {
        fields = new LinkedHashMap<>();
    }

    /**
     * Пример реализации конструктора в субклассах
     * @param jsonString
     *
    public DbObject(String jsonString) {
    super();
    addProperty("id", 0);
    addProperty("name",null);
    buildFromJSONString(jsonString);
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

    public Set<String> getProperties() {
        return fields.keySet();
    }

    public void buildFromJSONString(String jsonString) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
            buildFromJSON(jsonObject);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void buildFromJSON(JSONObject jsonObject) {
        for (String key: fields.keySet()) {
            Object value = jsonObject.get(key);
            setProperty(key, value);
        }
    }

    public String propertyToJSON(String key) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        sb.append(JSONObject.escape(key));
        sb.append("\":\"");
        sb.append(JSONObject.escape(getProperty(key).toString()));
        sb.append("\"");
        return sb.toString();
    }

    @Override
    public String toJSONString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        Set<String> properties = getProperties();
        Iterator<String> iterator = properties.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            sb.append(propertyToJSON(key));
            if(iterator.hasNext()) {
                sb.append(',');
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
