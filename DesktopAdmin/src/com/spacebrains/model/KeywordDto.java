package com.spacebrains.model;

import com.spacebrains.interfaces.IDbEntity;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

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
    public String toJSONString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append(JSONObject.escape("id"));
        sb.append(":");
        sb.append(this.getID());
        sb.append(",");
        sb.append(JSONObject.escape("name"));
        sb.append(":");
        sb.append("\"" + JSONObject.escape(this.getName()) + "\"");
        sb.append(",");
        sb.append(JSONObject.escape("person_id"));
        sb.append(":");
        sb.append(this.getPersonId());
        sb.append("}");
        return sb.toString();
    }
}
