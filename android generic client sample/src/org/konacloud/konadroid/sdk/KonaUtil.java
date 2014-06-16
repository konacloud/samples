package org.konacloud.konadroid.sdk;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.konacloud.konadroid.sdk.data.KonaView;

public class KonaUtil {

	
	public static KonaView view;
	
	public static JSONObject getJson(String url) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		String response = "";
		try {
			HttpResponse execute = client.execute(httpGet);
			InputStream content = execute.getEntity().getContent();

			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					content));
			String s = "";
			while ((s = buffer.readLine()) != null) {
				response += s;
			}

			JSONObject jObj = new JSONObject(response);
			return jObj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static JSONArray getJsonArray(String url) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		String response = "";
		try {
			HttpResponse execute = client.execute(httpGet);
			InputStream content = execute.getEntity().getContent();

			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					content));
			String s = "";
			while ((s = buffer.readLine()) != null) {
				response += s;
			}

			JSONArray jObj = new JSONArray(response);
			return jObj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
