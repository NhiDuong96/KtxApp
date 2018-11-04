package com.example.minhnhi.quanlyktx.utils;

import android.content.res.Resources;
import android.util.Log;

import com.example.minhnhi.quanlyktx.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonAPI {
	private static final String TAG = "JsonAPI";

	private JsonAPI(){}

	public static int post(String json, String path) throws IOException{
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
		os.close();

		int code = conn.getResponseCode();;
		conn.disconnect();
		return code;
	}

	public static String postForResponse(String json, String path, int successCode) throws IOException{
		Log.e(TAG, "send Json API: " + json);
		Log.e(TAG, "send path API: " + path);
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.connect();

		OutputStream os = conn.getOutputStream();
		os.write(json.getBytes());
		os.flush();
		os.close();

		int code = conn.getResponseCode();
		if(code == successCode){
			BufferedReader in=new BufferedReader(new
					InputStreamReader(
					conn.getInputStream()));

			StringBuilder sb = new StringBuilder();
			String line="";
			do{
				sb.append(line);
			}while ((line = in.readLine()) != null) ;

			in.close();
			Log.e("textmessage",sb.toString());
			return sb.toString();
		}
		else{
			throw new IOException(String.valueOf(code));
		}
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

	public static String getUri(Resources res, int uri){
		return res.getString(R.string.host) + res.getString(uri);
	}
}
