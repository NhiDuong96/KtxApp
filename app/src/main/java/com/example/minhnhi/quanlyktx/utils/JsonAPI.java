package com.example.minhnhi.quanlyktx.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class JsonAPI {
	private static final String TAG = "JsonAPI";

	private JsonAPI(){}

	public static void post(String json, String path) throws IOException{
		Log.e(TAG, "send Json API: " + json);
		Log.e(TAG, "send path API: " + path);
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");

		OutputStream os = conn.getOutputStream();
		os.write(json.getBytes());
		os.flush();

//		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
//			Log.e(TAG, "Failed : HTTP error code : " + conn.getResponseCode());
//			throw new IOException(String.valueOf(conn.getResponseCode()));
//		}
		conn.disconnect();
	}



	public static String get(String path) throws IOException {
		Log.e(TAG, "load Json API: "+path);
		StringBuilder data = new StringBuilder();
		URL url = new URL(path);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			Log.e(TAG, "Failed : HTTP error code : " + conn.getResponseCode());
			return "";
		}

		String line = "";
		while((line = reader.readLine()) != null){
			data.append(line);
		}

		conn.disconnect();
		return data.toString();
	}
}
