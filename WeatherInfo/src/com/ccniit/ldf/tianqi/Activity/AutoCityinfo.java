package com.ccniit.ldf.tianqi.Activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.ccniit.ldf.tianqi.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AutoCityinfo extends Activity {
	/** Called when the activity is first created. */
	ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
	Button button_check = null;
	Button button_cancle = null;
	String city_Id = null, city_Name = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.auto_cityinfo);
		button_check = (Button) findViewById(R.id.button_check);
		button_check.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(AutoCityinfo.this, MainActivity.class);
				if (city_Id != null) {
					intent.putExtra("cityName", city_Name);
					intent.putExtra("cityId", city_Id);
				}

				startActivity(intent);
				AutoCityinfo.this.finish();
				overridePendingTransition(android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);
			}

		});

		addItems();

		final AutoCompleteTextView ac = (AutoCompleteTextView) findViewById(R.id.autocomplete);
		SimpleAdapter notes = new SimpleAdapter(this, list,
				R.layout.main_item_three_line_rows, new String[] {
						"citySearchText", "cityName", "city_id" }, new int[] {
						R.id.citySearchText, R.id.cityName, R.id.city_id });
		ac.setAdapter(notes);
		ac.setThreshold(1);
		ac.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TextView tv = (TextView) arg1.findViewById(R.id.cityName);
				ac.setText(tv.getText().toString() + " ");
				ac.setSelection((ac.getText().toString()).length());
				TextView cid = (TextView) arg1.findViewById(R.id.city_id);
				TextView cname = (TextView) arg1.findViewById(R.id.cityName);
				if (cid.getText().length() != 0) {
					city_Name = cname.getText().toString().trim();
					city_Id = cid.getText().toString();
				}
			}

		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setClass(AutoCityinfo.this, MainActivity.class);
			startActivity(intent);
			AutoCityinfo.this.finish();
			overridePendingTransition(android.R.anim.slide_in_left,
					android.R.anim.slide_out_right);
		}
		return false;
	}

	/**
	 * 城市数据
	 */
	private void addItems() {
		HashMap<String, String> item;

		item = new HashMap<String, String>();
		item.put("citySearchText", "BEIJING beijing BEJ");
		item.put("cityName", "北京");
		item.put("city_id", "101010100");
		list.add(item);

		item = new HashMap<String, String>();
		item.put("citySearchText", "HAIDING haiding HD");
		item.put("cityName", "海淀");
		item.put("city_id", "101010200");

		list.add(item);

		item = new HashMap<String, String>();
		item.put("citySearchText", "chaoyang cy CY CHAOYANG");
		item.put("cityName", "朝阳");
		item.put("city_id", "101010300");
		list.add(item);

		item = new HashMap<String, String>();
		item.put("citySearchText", "SY shunyi SHUNYI sy ");
		item.put("cityName", "顺义");
		item.put("city_id", "101010400");
		list.add(item);

		item = new HashMap<String, String>();
		item.put("citySearchText", "HR huairou HUAIROU hr ");
		item.put("cityName", "怀柔");
		item.put("city_id", "101010500");
		list.add(item);

		item = new HashMap<String, String>();
		item.put("citySearchText", "tz TZ  tongzhou TONGZHOU ");
		item.put("cityName", "通州");
		item.put("city_id", "101010600");
		list.add(item);

		item = new HashMap<String, String>();
		item.put("citySearchText", "cp CP changping CHANGPING");
		item.put("cityName", "昌平");
		item.put("city_id", "101010700");
		list.add(item);

		item = new HashMap<String, String>();
		item.put("citySearchText", "YQ YANQING yq yanqin ");
		item.put("cityName", "延庆");
		item.put("city_id", "101010800");
		list.add(item);

		item = new HashMap<String, String>();
		item.put("citySearchText", "fengtai fengtai ft FT");
		item.put("cityName", "丰台");
		item.put("city_id", "101010900");
		list.add(item);

		item = new HashMap<String, String>();
		item.put("citySearchText", "SJS sjs SHIJINGSHAN shijingshan");
		item.put("cityName", "石景山");
		item.put("city_id", "101011000");
		list.add(item);

		item = new HashMap<String, String>();
		item.put("citySearchText", "SH SHANGHAI shanghai sh");
		item.put("cityName", "上海");
		item.put("city_id", "101020100");
		list.add(item);

		item = new HashMap<String, String>();
		item.put("citySearchText", "tianjin TJ TIANJIN tj");
		item.put("cityName", "天津");
		item.put("city_id", "101030100");
		list.add(item);

		item = new HashMap<String, String>();
		item.put("citySearchText", "chongqing CHONGQING CQ cq");
		item.put("cityName", "重庆");
		item.put("city_id", "101040100");
		list.add(item);

		item = new HashMap<String, String>();
		item.put("citySearchText", "haerbin hrb HRB HAERBIN");
		item.put("cityName", "哈尔滨");
		item.put("city_id", "101050101");
		list.add(item);

		item = new HashMap<String, String>();
		item.put("citySearchText", "cc CC CHANGCHUN changchun");
		item.put("cityName", "长春");
		item.put("city_id", "101060101");
		list.add(item);

		item = new HashMap<String, String>();
		item.put("citySearchText", "sy SY shenyang SHENGYANG");
		item.put("cityName", "沈阳");
		item.put("city_id", "101070101");
		list.add(item);

		item = new HashMap<String, String>();
		item.put("citySearchText", "HHHT hhht huhehaote HUHEHAOTE");
		item.put("cityName", "呼和浩特");
		item.put("city_id", "101080101");
		list.add(item);

		item = new HashMap<String, String>();
		item.put("citySearchText", "SJZ sjz shijiazhuang SHIJIAZHUANG");
		item.put("cityName", "石家庄");
		item.put("city_id", "101090101");
		list.add(item);

		item = new HashMap<String, String>();
		item.put("citySearchText", "TAIYUAN TY taiyuan ty");
		item.put("cityName", "太原");
		item.put("city_id", "101100101");
		list.add(item);

		item = new HashMap<String, String>();
		item.put("citySearchText", "xian XA XIAN xa");
		item.put("cityName", "西安");
		item.put("city_id", "101110101");
		list.add(item);

		item = new HashMap<String, String>();
		item.put("citySearchText", "CD cd CHENGDU chengdu");
		item.put("cityName", "成都");
		item.put("city_id", "101270101");
		list.add(item);

		item = new HashMap<String, String>();
		item.put("citySearchText", "bz BZ bazhong BAZHONG");
		item.put("cityName", "巴中");
		item.put("city_id", "101270901");
		list.add(item);

	}

}