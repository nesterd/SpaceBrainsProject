package com.spacebrains.model;

import com.spacebrains.interfaces.IDbEntity;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class KeywordDto extends Keyword implements IDbEntity, JSONAware {
    private int personId = 0;

    public KeywordDto(int keywordId, String name, int personId) {
        super(keywordId, name);
        this.personId = personId;
    }

    public KeywordDto(Keyword keyword) {
        super(keyword.getID(), keyword.getName(), keyword.getPerson());
        this.personId = keyword.getPerson().getID();
    }

    public int getPersonId() {
        return  personId;
    }

    @Override
    public String getEntityTypeString() {
        return new String("keyword");
    }

    @Override
    public void fromJSONString(String jsonString) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) (parser.parse(jsonString));
            int id = (int) obj.get("id");
            String name = (String) obj.get("name");
            int personId = (int) obj.get("person_id");
            this.setID(id);
            this.setName(name);
            this.setPerson(new Person(personId, name));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toJSONString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"");
        sb.append(JSONObject.escape("id"));
        sb.append("\": ");
        sb.append(this.getID());
        sb.append(", \"");
        sb.append(JSONObject.escape("name"));
        sb.append("\": \"");
        sb.append(JSONObject.escape(this.getName()));
        sb.append("\", \"");
        sb.append(JSONObject.escape("person_id"));
        sb.append("\": ");
        sb.append(this.getPersonId());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public String nameToJSONString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ \"");
        sb.append(JSONObject.escape("name"));
        sb.append("\":\"");
        sb.append(JSONObject.escape(this.getName()));
        sb.append("\"}");
        return sb.toString();
    }
}
