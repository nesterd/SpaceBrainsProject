package com.spacebrains.core.rest;

import com.spacebrains.core.util.BaseParams;
import com.spacebrains.model.Keyword;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;


/**
 * The {@Code RestConnector} class implements interaction with RESTful API
 * @author Oleg Chizhov
 */
public class RestConnector implements ResponseHandler<JSONObject> {
    private int httpStatus = 0;
    private String resultString = null;
    private CloseableHttpClient httpclient = null;
    private JSONObject jsonObject = null;

    public RestConnector() {
        //CloseableHttpClient httpclient = HttpClients.createDefault();
    }

    /**
     * Closes active httpClient if any
     */
    public void close() {
        try {
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the HTTP Response status
     * @return the HTTP Response status
     */
    public int getState() { return httpStatus; }

    /**
     * Returns inner JSONObject
     * @return inner JSONObject
     */
    public JSONObject getJsonObject() {
        return jsonObject;
    }

    /**
     * Returns the raw response JSON string
     * @return the raw response JSON string
     */
    public String getResultString() { return resultString; }

    /**
     * Desereializes the Keyword object from JSON string
     * @return the Keyword object
     */
    public Keyword getKeywordFromJSON(String jsonString) {
        return null;
    }

    /**
     * Excutes GET command on REST web service
     * @param commandUri
     *        string with REST request
     */
    public void sendGetCommand(String commandUri) {
        System.out.println(getRestURL(commandUri));
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(getRestURL(commandUri));
            JSONObject responseBody = httpclient.execute(httpget, this);
            jsonObject = responseBody;
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Excutes DEL command on REST web service
     * @param commandUri
     *        string with REST request
     */
    public void sendDelCommand(String commandUri) {
        System.out.println(getRestURL(commandUri));
        try {
            HttpDelete httpDelete = new HttpDelete(getRestURL(commandUri));
            JSONObject responseBody = httpclient.execute(httpDelete, this);
            jsonObject = responseBody;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Excutes PUT command on REST web service
     * @param commandUri
     *        string with REST request
     */
    public void sendPutCommand(String commandUri) {
        System.out.println(getRestURL(commandUri));
        StringEntity outEntity = new StringEntity(jsonObject.toJSONString(), ContentType.APPLICATION_JSON);
        try {
            HttpPut httpPut = new HttpPut(getRestURL(commandUri));
            JSONObject responseBody = httpclient.execute(httpPut, this);
            jsonObject = responseBody;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getRestURL(String commandUri) {
        return BaseParams.BaseURL + commandUri;
    }

    @Override
    public JSONObject handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
        httpStatus = httpResponse.getStatusLine().getStatusCode();
        JSONObject jsonResponse = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        if (httpStatus >= 200 && httpStatus <= 300) {
            HttpEntity entity = httpResponse.getEntity();
            try {
                if(null == entity) {
                    jsonResponse.put("status_code", "1");
                    jsonResponse.put("error_message", "null Data Found");
                } else {
                    jsonResponse = (JSONObject) jsonParser.parse(EntityUtils.toString(entity));
                }
            } catch (ParseException e) {
                jsonResponse.put("status_code", "1");
                jsonResponse.put("error_message", e.getMessage());
            }
        } else {
            jsonResponse.put("status_code", "1");
            jsonResponse.put("error_message", "Unexpected response status: " + httpStatus);
        }
        return jsonResponse;
    }
}
