package com.spacebrains.core.http;

import com.spacebrains.core.util.RestURIBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class HttpProvider {
	private String innerJSONstring = null;
	private static HttpProvider instance;
	
	public HttpProvider() {
		 
	}

	public static HttpProvider getInstance() {
		if(instance == null) {
			instance = new HttpProvider();
		}
		return instance;
	}
	
	public String getJSONString() {
		return innerJSONstring;
	}

	/*
	 *  Unsafe method - it's better to add JSON parsing ability to be sure
	 *  that innerJSONString complies to JSON specification
	 *  Use only for debug
	 */
	public void putJSONString(String inputJSONString) {
		innerJSONstring = inputJSONString;
	}
	
	/*
	 * Prepares HttpEntity object from given JSON string
	 */
	private StringEntity prepareEntity(String inputJSONString) {
		StringEntity entity = null;
		entity = new StringEntity(inputJSONString, "utf-8");
		entity.setContentType("application/json");
		return entity;
	}

	/**
	 * Constructs new connection and executes one of the HTTP methods
	 * @param request HttpRequestBase object - request method type
	 * @param requestURIString  String - URI
	 * @return HttpStatus constant (int)
	 */
	private int doRequest(HttpRequestBase request, String requestURIString) {
		CloseableHttpClient client = HttpClients.createDefault();
		request.setURI(RestURIBuilder.buildURI(requestURIString));
		CloseableHttpResponse response = null;
		int status = 0;
		try {
			response = client.execute(request);
			status = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
//			response.getStatusLine();
			if(entity != null) {
				long len = entity.getContentLength();
				if(len != -1) {
					innerJSONstring = EntityUtils.toString(entity);
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				response.close();
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return status;
	}

	public int doGetMethod(String requestURIString) {
        HttpGet httpGet = new HttpGet();
        return doRequest(httpGet, requestURIString);
//		innerJSONstring = new String("{ \"person\": [ { \"id\": 1, \"name\": \"lenta.ru\" }, { \"id\": 2, \"name\": \"rbk.ru\" } ] }");
//		innerJSONstring = new String("{ \"id\": 1, \"keywords\": [ { \"id\": 1, \"name\": \"Медведев\", \"person_id\": 2 }, { \"id\": 2, \"name\": \"Медведеву\", \"person_id\": 2 } ] }");
//		return HttpStatus.SC_OK;
	}
	
	public int doPutMethod(String requestURIString) {
		StringEntity requestEntity = null;
		if(innerJSONstring == null) {
			return 0;
		}
		requestEntity = prepareEntity(innerJSONstring);
		HttpPut httpPut = new HttpPut();
		httpPut.setEntity(requestEntity);
		int status = doRequest(httpPut, requestURIString);
		httpPut.setURI(RestURIBuilder.buildURI(requestURIString));
		return status;
	}
	
	public int doDeleteMethod(String requestURI) {
		HttpDelete httpDelete = new HttpDelete();
		return doRequest(httpDelete, requestURI);
	}
}
