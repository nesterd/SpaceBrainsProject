package com.spacebrains.model;

import com.spacebrains.interfaces.IDbEntity;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PersonDto extends Person implements IDbEntity, JSONAware {

    public PersonDto(int personId, String name) {
        super(personId, name);
    }

    public PersonDto(String name) {
        super(name);
    }

    @Override
    public String toJSONString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(JSONObject.escape("id"));
        sb.append(":");
        sb.append(this.getID());
        sb.append(",");
        sb.append(JSONObject.escape("name"));
        sb.append(":");
        sb.append("\"" + JSONObject.escape(this.getName()) + "\"");
        sb.append("}");
        return sb.toString();
    }

    @Override
    public String getEntityTypeString() {
        return new String("person");
    }

    @Override
    public void fromJSONString(String jsonString) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) (parser.parse(jsonString));
            int id = (int) obj.get("id");
            String name = (String) obj.get("name");
            this.setID(id);
            this.setName(name);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
