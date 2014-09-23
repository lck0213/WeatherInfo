package com.ccniit.ldf.tianqi.Activity;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ccniit.ldf.tianqi.R;
import com.ccniit.ldf.tianqi.Util.ChangerUtils;
import com.ccniit.ldf.tianqi.Util.JsonUtils;
import com.ccniit.ldf.tianqi.Util.WeatherInfo;

/**
 * * 纯新手，思想有点混乱 大家看了 有什么不好的地方 求大家给点意见。 谢谢了。
 * 
 * @author phil
 * 
 */

public class MainActivity extends Activity {
	TextView textView_city, text_updateTime, text_currTemp_in,
			text_currHumidity_in, text_currWindCondition_in,
			text_currAdvice_in;
	TextView[] view_Weekdays, view_Temps, view_Weathers;
	ImageView[] imgs;
	int[] img_res_ids;
	boolean is_Saved = false;
	// 信息存储
	String city_Name_Main, city_Id_Main, temp_Main, wind_direction_MAIN,
			humidity_Main, date_Main, weather_01_Main, weather_02_Main,
			weather_03_Main, weather_04_Main, weather_05_Main, temp_02_Main,
			temp_03_Main, temp_04_Main, temp_05_Main, adivise_Main,
			refersh_time, day_of_week_Main;
	String[] weekdays_Main, temps_Main;
	WeatherInfo info;
	private Thread mthread;
	private static final int MSG_SUCCESS = 0;// 获取信息的标识
	private static final int MSG_FAILURE = 1;// 获取信息的标识
	/** Called when the activity is first created. */
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_FAILURE:
				Toast.makeText(getApplication(), "更新失败", Toast.LENGTH_SHORT)
						.show();
				break;
			case MSG_SUCCESS:
				Toast.makeText(getApplication(), "更新成功", Toast.LENGTH_SHORT)
						.show();
				info = (WeatherInfo) msg.obj;
				refersh_time = getDate();
				changeUI(info);

			}
		}
	};

	Runnable runnable = new Runnable() {
		public void run() {
			// TODO Auto-generated method stub
			WeatherInfo info = refresh(city_Id_Main);
			if (info != null) {
				mHandler.obtainMessage(MSG_SUCCESS, info).sendToTarget();
			} else {
				mHandler.obtainMessage(MSG_FAILURE).sendToTarget();
				return;
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		textView_city = (TextView) findViewById(R.id.text_city);
		text_updateTime = (TextView) findViewById(R.id.text_updateTime);
		text_currTemp_in = (TextView) findViewById(R.id.text_currTemp_in);
		// text_currWeather = (TextView) findViewById(R.id.text_currWeather);
		text_currWindCondition_in = (TextView) findViewById(R.id.text_currWindCondition_in);
		text_currHumidity_in = (TextView) findViewById(R.id.text_currHumidity_in);
		text_currAdvice_in = (TextView) findViewById(R.id.text_currAdvice_in);

		view_Weathers = findTweathers();
		imgs = findImageView();
		view_Weekdays = findWeekDaysView();
		view_Temps = findTemps();

		Intent intent = this.getIntent();
		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		is_Saved = preferences.getBoolean("is_Saved", false);
		// 判断intent中是否有值
		if (is_Saved) {
			changeUI(getInfo());

		}
		if (intent.hasExtra("cityId")) {
			city_Name_Main = intent.getStringExtra("cityName");
			city_Id_Main = intent.getStringExtra("cityId");

			mthread = new Thread(runnable);
			mthread.start();
		}

		/**
		 * 更换城市
		 */
		textView_city.setOnClickListener(new TextView.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, AutoCityinfo.class);
				startActivity(intent);
				MainActivity.this.finish();

			}
		});

		/**
		 * 刷新操作
		 */
		final Button refresh = (Button) findViewById(R.id.button_refresh);
		OnClickListener listener = new OnClickListener() {

			public void onClick(View v) {
				RotateAnimation ra = new RotateAnimation(0, 720,
						Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f);
				mthread = new Thread(runnable);
				mthread.start();
				ra.setDuration(3000);// 旋转时间
				refresh.startAnimation(ra);
				Toast.makeText(MainActivity.this, "正在更新", Toast.LENGTH_LONG)
						.show();

			}
		};
		refresh.setOnClickListener(listener);

	}

	/**
	 * 获取图片
	 * 
	 * @return
	 */
	public ImageView[] findImageView() {
		int[] image = { R.id.img_currImage, R.id.img_01_imageView,
				R.id.img_02_imageView, R.id.img_03_imageView,
				R.id.img_04_imageView };

		ImageView[] imageView = new ImageView[image.length];

		for (int i = 0; i < imageView.length; i++) {
			imageView[i] = (ImageView) findViewById(image[i]);
		}
		return imageView;

	}

	public TextView[] findWeekDaysView() {
		int[] textView = { R.id.text_01_day_of_week, R.id.text_02_day_of_week,
				R.id.text_03_day_of_week, R.id.text_04_day_of_week };

		TextView[] textView_weekdays = new TextView[textView.length];

		for (int i = 0; i < textView.length; i++) {
			textView_weekdays[i] = (TextView) findViewById(textView[i]);
		}
		return textView_weekdays;

	}

	public TextView[] findTemps() {
		int[] textView = { R.id.text_01_temp_in, R.id.text_02_temp_in,
				R.id.text_03_temp_in, R.id.text_04_temp_in };

		TextView[] textView_weekdays = new TextView[textView.length];

		for (int i = 0; i < textView.length; i++) {
			textView_weekdays[i] = (TextView) findViewById(textView[i]);
		}
		return textView_weekdays;

	}

	public TextView[] findTweathers() {
		int[] textView = { R.id.text_currWeather, R.id.text_01_weather_in,
				R.id.text_02_weather_in, R.id.text_03_weather_in,
				R.id.text_04_weather_in };

		TextView[] textView_weathers = new TextView[textView.length];

		for (int i = 0; i < textView.length; i++) {
			textView_weathers[i] = (TextView) findViewById(textView[i]);
		}
		return textView_weathers;

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// BACK键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			System.out.println(refersh_time == null);

			if (refersh_time != null) {
				saveInfo();
			}
			MainActivity.this.finish();

			return false;
		}
		return false;
	}

	public WeatherInfo refresh(String cityid) {
		if (cityid == null) {
			cityid = "101010100";
		}
		WeatherInfo info = null;
		JsonUtils jsonUtils = new JsonUtils();
		String strurl_1 = "http://www.weather.com.cn/data/sk/" + cityid
				+ ".html";
		String strUrl_2 = "http://m.weather.com.cn/data/" + cityid + ".html";
		String strResult_1 = jsonUtils.connServerForResult(strurl_1);
		String strResult_2 = jsonUtils.connServerForResult(strUrl_2);
		if (strResult_1.length() != 0 && strResult_2.length() != 0) {
			info = jsonUtils.parseJson(strResult_2,
					jsonUtils.parseJson(strResult_1));

		}
		return info;
	}

	public String getDate() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd hh:mm");
		String date = "更新时间:" + sDateFormat.format(new java.util.Date());
		System.out.println(date);
		return date;
	}

	public void changeUI(WeatherInfo infos) {
		ChangerUtils changerUtils = new ChangerUtils();
		city_Name_Main = infos.getCity();
		adivise_Main = infos.getAdivise();
		day_of_week_Main = infos.getDate();
		humidity_Main = infos.getHumidity();
		wind_direction_MAIN = infos.getWind_direction();
		temp_Main = infos.getTemp();
		String[] temps = { infos.getTemp_02(), infos.getTemp_03(),
				infos.getTemp_04(), infos.getTemp_05() };
		temps_Main = temps;
		weather_01_Main = infos.getWeather_01();
		weather_02_Main = infos.getWeather_02();
		weather_03_Main = infos.getWeather_03();
		weather_04_Main = infos.getWeather_04();
		weather_05_Main = infos.getWeather_05();
		weekdays_Main = changerUtils.weekDays(changerUtils.matchDayofWeek(infos
				.getDate()));
		String[] infos_weathers = { infos.getWeather_01(),
				infos.getWeather_02(), infos.getWeather_03(),
				infos.getWeather_04(), infos.getWeather_05() };
		img_res_ids = changerUtils.serchImage(infos_weathers);

		for (int i = 0; i < imgs.length; i++) {
			imgs[i].setImageResource(img_res_ids[i]);
			view_Weathers[i].setText(infos_weathers[i]);
		}
		for (int i = 0; i < weekdays_Main.length; i++) {
			view_Weekdays[i].setText(weekdays_Main[i]);
			view_Temps[i].setText(temps[i]);

		}
		textView_city.setText(city_Name_Main);
		text_updateTime.setText(refersh_time);
		text_currTemp_in.setText(temp_Main);
		text_currAdvice_in.setText(adivise_Main);
		text_currWindCondition_in.setText(wind_direction_MAIN);
		text_currHumidity_in.setText(humidity_Main);
		// text_currWeather.setText(weather_01_Main);

	}

	/**
	 * 保存数据
	 */
	public void saveInfo() {
		SharedPreferences uiState = getPreferences(0);
		SharedPreferences.Editor editor = uiState.edit();
		editor.putBoolean("is_Saved", true);
		editor.putString("adivise_Main", adivise_Main);
		editor.putString("city_Name_Main", city_Name_Main);
		editor.putString("humidity_Main", humidity_Main);
		editor.putString("wind_direction_MAIN", wind_direction_MAIN);
		editor.putString("temp_Main", temp_Main);
		editor.putString("temps_Main_0", temps_Main[0]);
		editor.putString("temps_Main_1", temps_Main[1]);
		editor.putString("temps_Main_2", temps_Main[2]);
		editor.putString("temps_Main_3", temps_Main[3]);
		editor.putString("weather_01_Main", weather_01_Main);
		editor.putString("weather_02_Main", weather_02_Main);
		editor.putString("weather_03_Main", weather_03_Main);
		editor.putString("weather_04_Main", weather_04_Main);
		editor.putString("weather_05_Main", weather_05_Main);
		editor.putString("city_id", city_Id_Main);
		editor.putString("refersh_time", refersh_time);
		editor.putString("day_of_week_Main", day_of_week_Main);
		editor.commit();

	}

	/**
	 * 获得保存数据
	 * 
	 * @return info
	 */
	public WeatherInfo getInfo() {
		WeatherInfo weatherInfo = new WeatherInfo();
		SharedPreferences sharedPreference = getPreferences(MODE_PRIVATE);
		weatherInfo.setCity(sharedPreference.getString("city_Name_Main", ""));
		weatherInfo
				.setHumidity(sharedPreference.getString("humidity_Main", ""));
		weatherInfo.setWind_direction(sharedPreference.getString(
				"wind_direction_MAIN", ""));
		weatherInfo.setTemp(sharedPreference.getString("temp_Main", ""));
		weatherInfo.setTemp_02(sharedPreference.getString("temps_Main_0", ""));
		weatherInfo.setTemp_03(sharedPreference.getString("temps_Main_1", ""));
		weatherInfo.setTemp_04(sharedPreference.getString("temps_Main_2", ""));
		weatherInfo.setTemp_05(sharedPreference.getString("temps_Main_3", ""));
		weatherInfo.setWeather_01(sharedPreference.getString("weather_01_Main",
				""));
		weatherInfo.setWeather_02(sharedPreference.getString("weather_02_Main",
				""));
		weatherInfo.setWeather_03(sharedPreference.getString("weather_03_Main",
				""));
		weatherInfo.setWeather_04(sharedPreference.getString("weather_04_Main",
				""));
		weatherInfo.setWeather_05(sharedPreference.getString("weather_05_Main",
				""));
		weatherInfo.setAdivise(sharedPreference.getString("adivise_Main", ""));
		refersh_time = sharedPreference.getString("refersh_time", "");
		city_Id_Main = sharedPreference.getString("city_id", "");
		weatherInfo.setDate(sharedPreference.getString("day_of_week_Main", ""));
		return weatherInfo;
	}

}
