package com.spacebrains.model;

import com.spacebrains.interfaces.IDbEntity;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class SiteDto extends Site implements IDbEntity, JSONAware {
    public SiteDto(int siteId, String name) {
        super(siteId, name);
    }

    public SiteDto(String name) {
        super(name);
    }

    @Override
    public String getEntityTypeString() {
        return new String("site");
    }

    @Override
    public void fromJSONString(String jsonString) {
        //TODO: implement this method
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
}
