package com.spacebrains.model;

import com.spacebrains.interfaces.IDbEntity;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SiteDto extends Site implements IDbEntity, JSONAware {
    public SiteDto(int siteId, String name) {
        super(siteId, name);
    }

    public SiteDto(String name) {
        super(name);
    }

    public SiteDto(Site site) {
        super(site.getID(), site.getName());
    }

    @Override
    public String getEntityTypeString() {
        return new String("site");
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

    @Override
    public String toJSONString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"");
        sb.append(JSONObject.escape("id"));
        sb.append("\":");
        sb.append(this.getID());
        sb.append(",\"");
        sb.append(JSONObject.escape("name"));
        sb.append("\":\"");
        sb.append(JSONObject.escape(this.getName()));
        sb.append("\"}");
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
