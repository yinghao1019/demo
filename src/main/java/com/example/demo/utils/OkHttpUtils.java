package com.example.demo.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;

import exception.InternalServerErrorException;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtils {

	private static final long TIMEOUT_IN_SEC = 30;

	private static final Headers defaultHeader = Headers.of("Content-Type", "application/json");

	public void OkHttpUtils() {
	}

	public static String postRequest(String uri, String queryJson, Map<String, String> headers) throws IOException {
		RequestBody body = RequestBody.create(queryJson, MediaType.parse("application/json;charset=utf-8"));
		Request request = new Request.Builder().url(uri).headers(getHeaders(headers)).post(body).build();
		return sendRequest(request);
	}

	public static String postRequest(String uri, Map<String, String> formDataParams, Map<String, String> headers)
			throws IOException {
		Request request = new Request.Builder().url(uri).headers(getHeaders(headers)).post(getFormBody(formDataParams))
				.build();
		return sendRequest(request);
	}
	

	public static String getRequest(String uri, Map<String, String> params, Map<String, String> headers)
			throws IOException {
		HttpUrl.Builder httpBuilder = HttpUrl.parse(uri).newBuilder();
		if (params != null) {
			for (Map.Entry<String, String> param : params.entrySet()) {
				httpBuilder.addQueryParameter(param.getKey(), param.getValue());
			}
		}

		Request request = new Request.Builder().url(httpBuilder.build()).headers(getHeaders(headers)).get().build();
		return sendRequest(request);
	}

	private static Headers getHeaders(Map<String, String> headerMap) {
		Map<String, String> currentHeaderMap = new HashMap<>();
		// 加上預設 Header
		for (int i = 0; i < defaultHeader.size(); i++) {
			currentHeaderMap.put(defaultHeader.name(i), defaultHeader.value(i));
		}

		if (headerMap != null) {
			for (Map.Entry<String, String> header : headerMap.entrySet()) {
				currentHeaderMap.put(header.getKey(), header.getValue());
			}
		}
		return Headers.of(currentHeaderMap);
	}
	
    private static FormBody getFormBody(Map<String, String> formDataParams) {
        var formBody = new FormBody.Builder();
        if (formDataParams != null) {
            for (Map.Entry<String, String> header : formDataParams.entrySet()) {
                formBody.add(header.getKey(), header.getValue());
            }
        }
        return formBody.build();
    }


	private static String sendRequest(Request request) throws IOException {
		OkHttpClient client = newInstance();
		Response response = client.newCall(request).execute();
		int statusCode = response.code();
		String responseBody = null;
		if (response.body() != null) {
			responseBody = response.body().string();
		}
		String msg = String.format("Url: %s, Status Code: %s, Response Body: %s", request.url().toString(), statusCode,
				responseBody);
		if (statusCode != 200 && statusCode != 204) {
			throw new InternalServerErrorException(msg);
		}
		return responseBody;
	}

	private static OkHttpClient newInstance() {
    	var clientBuilder = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_IN_SEC, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_IN_SEC, TimeUnit.SECONDS);

            if (Boolean.TRUE.equals(false)) {
                // 忽略憑證驗證
                
            }
            return clientBuilder.build();
        }
}
