package com.ccniit.ldf.tianqi.Util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

	public String connServerForResult(String strUrl) {

		// HttpGet对象

		HttpGet httpRequest = new HttpGet(strUrl);

		String strResult = "";

		try {

			// HttpClient对象
			HttpClient httpClient = new DefaultHttpClient();
			HttpParams params = httpClient.getParams();
			// 请求超时5秒 接受超时5秒
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			HttpConnectionParams.setSoTimeout(params, 5000);
			// 获得HttpResponse对象
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				// 取得返回的数据

				strResult = EntityUtils.toString(httpResponse.getEntity());

			} else {
				return strResult;
			}

		} catch (ClientProtocolException e) {

			System.out.println("protocol error");

			e.printStackTrace();

		} catch (IOException e) {

			System.out.println("IO error");

			e.printStackTrace();

		}
		return strResult;

	}

	public WeatherInfo parseJson(String strResult) {
		WeatherInfo info = null;
		try {
			JSONObject jsonObj = new JSONObject(strResult)
					.getJSONObject("weatherinfo");

			String city = jsonObj.getString("city");

			String temp = jsonObj.getString("temp") + "℃";

			String wind_direction = jsonObj.getString("WD");

			String humidity = jsonObj.getString("SD");

			info = new WeatherInfo();
			info.setCity(city);
			info.setTemp(temp);
			info.setWind_direction(wind_direction);
			info.setHumidity(humidity);

		} catch (JSONException e) {

			System.out.println("Json parse error");

			e.printStackTrace();

		}

		return info;

	}

	public WeatherInfo parseJson(String strResult, WeatherInfo info) {
		try {
			JSONObject jsonObj = new JSONObject(strResult)
					.getJSONObject("weatherinfo");
			String date = jsonObj.getString("week");
			String weather_01 = jsonObj.getString("weather1");

			String weather_02 = jsonObj.getString("weather2");

			String weather_03 = jsonObj.getString("weather3");

			String weather_04 = jsonObj.getString("weather4");

			String weather_05 = jsonObj.getString("weather5");

			String temp_02 = jsonObj.getString("temp2");

			String temp_03 = jsonObj.getString("temp3");

			String temp_04 = jsonObj.getString("temp4");

			String temp_05 = jsonObj.getString("temp5");

			String adivise = jsonObj.getString("index_d");
			info.setWeather_01(weather_01);
			info.setWeather_02(weather_02);
			info.setWeather_03(weather_03);
			info.setWeather_04(weather_04);
			info.setWeather_05(weather_05);
			info.setTemp_02(temp_02);
			info.setTemp_03(temp_03);
			info.setTemp_04(temp_04);
			info.setTemp_05(temp_05);
			info.setAdivise(adivise);
			info.setDate(date);

		} catch (JSONException e) {

			System.out.println("Json parse error");

			e.printStackTrace();

		}

		return info;

	}

}
