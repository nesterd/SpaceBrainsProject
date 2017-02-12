package com.spacebrains.core.http;

import com.spacebrains.core.AuthConstants;
import com.spacebrains.core.util.RestURIBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Класс применяется для вызова стандартных методов http: GET, PUT, POST, DELETE
 * с учетом специфики реализации RESTful web-сервиса
 * реализует паттерн синглтон
 */
public class HttpProvider {
	private String innerJSONstring = null;
	private String statusLine = null;
	private static HttpProvider instance;
	private String token = null;

	public static HttpProvider getInstance() {
		if(instance == null) {
			instance = new HttpProvider();
		}
		return instance;
	}

	public String getStatusLine() {
		return statusLine;
	}

	/**
	 * Читает внутреннюю строку, которая должна соответствовать спецификации JSON
	 * для корректной работы конвертеров
	 * @return возвращает String с содержимым внутренней строки
	 */
	public String getJSONString() {
		return innerJSONstring;
	}

	/*
	 *  Mostly unsafe method - it's better to add JSON parsing ability to be sure
	 *  that innerJSONString complies to JSON specification
	 */
	public void setJSONString(String inputJSONString) {
		innerJSONstring = inputJSONString;
		System.out.println(inputJSONString);
	}

	/**
	 * Подготавливает сущность StringEntity для запроса с параметрами 
	 * @param inputJSONString строка с подготовленным JSON для преобразования
	 * @return строковая сущность, которая отправляется затем в теле запроса
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
	 * @return HttpStatus constant (String)
	 */
	private int doRequest(HttpRequestBase request, String requestURIString) {
		CloseableHttpClient client = HttpClients.createDefault();
		request.setURI(RestURIBuilder.buildURI(requestURIString));

		// for debug purposes :
		System.out.println(request.getRequestLine());
		CloseableHttpResponse response = null;
		int status = 0;
		statusLine = null;
		try {
			if(token != null)
				request.setHeader("Authorization", "JWT " + token);
			response = client.execute(request);
			status = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			System.out.println(response.getStatusLine());
			if(entity != null) {
				long len = entity.getContentLength();
				if(len != -1) {
					innerJSONstring = EntityUtils.toString(entity);
				}
			}
		} catch (ClientProtocolException | HttpHostConnectException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//			statusLine = e.getMessage();
			throw new RuntimeException(AuthConstants.NOT_ANSWERED);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			statusLine = e.getMessage();
		} finally {
			try {
				if (response != null) response.close();
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
	}
	
	public int doPutMethod(String requestURIString) {
		StringEntity requestEntity = null;
		if(innerJSONstring == null) {
			return 0;
		}
		requestEntity = prepareEntity(innerJSONstring);
		HttpPut httpPut = new HttpPut();
		httpPut.setEntity(requestEntity);
		return doRequest(httpPut, requestURIString);
	}
	
	public int doDeleteMethod(String requestURI) {
		HttpDelete httpDelete = new HttpDelete();
		return doRequest(httpDelete, requestURI);
	}

	public int doPostMethod(String requestURIString) {
		StringEntity requestEntity = null;
		if(innerJSONstring == null) {
			return 0;
		}
		requestEntity = prepareEntity(innerJSONstring);
		HttpPost httpPost = new HttpPost();
		httpPost.setEntity(requestEntity);
		return doRequest(httpPost, requestURIString);
	}

	/**
	 * Set up Authorization token it httpRequest
	 * @param token string value of secret JWT token
	 */
	public void authorize(String token) {
		this.token = token;
	}
}
