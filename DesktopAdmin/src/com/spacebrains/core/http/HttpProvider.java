package com.spacebrains.core.http;

import com.spacebrains.core.util.RestURIBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HttpProvider {
	private String innerJSONstring = null;
	
	public HttpProvider() {
		 
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
		try {
			entity = new StringEntity(inputJSONString);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}
	
	public void doGetRequest(String requestURIString) {
		CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet();
        httpGet.setURI(RestURIBuilder.buildURI(requestURIString));
		CloseableHttpResponse response = null;
		try {
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity != null) {
				long len = entity.getContentLength();
				if(len != -1 && len < 2048) {
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
	}
	
	public void doPutRequest(String requestURIString) {
		StringEntity requestEntity = null;
		if(innerJSONstring == null) {
			return;
		}
		requestEntity = prepareEntity(innerJSONstring);
		requestEntity.setContentType("application/json");
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut();
		httpPut.setURI(RestURIBuilder.buildURI(requestURIString));
		httpPut.setEntity(requestEntity);
		CloseableHttpResponse response = null;
		try {
			response = client.execute(httpPut);
			HttpEntity entity = response.getEntity();
			if(entity != null) {
				long len = entity.getContentLength();
				if(len != -1 && len < 2048) {
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
	}
	
	public void doDeleteRequest(String requestURI) {
		//TODO: implement doDeleteRequest
	}
}
