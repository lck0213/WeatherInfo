package com.ccniit.ldf.tianqi.Util;

import com.ccniit.ldf.tianqi.R;

public class ChangerUtils {
	final String CLEAR = "晴";
	final String RAIN = "雨";
	final String SNOW = "雪";
	final String CLOUDY = "云";
	final String THUNDERSTORMS = "大雨";
	final String FOG = "雾";
	final String HAZE = "霾";
	final String OVERCAST = "阴";

	public int[] serchImage(String[] weathers) {
		int[] imgids = new int[5];
		for (int i = 0; i < weathers.length; i++) {

			if (weathers[i].indexOf(CLOUDY) != -1) {
				imgids[i] = R.drawable.w_cloudy;
				continue;
			} else if (weathers[i].indexOf(OVERCAST) != -1) {
				imgids[i] = R.drawable.w_overcast;
				continue;
			} else if (weathers[i].indexOf(RAIN) != -1) {
				if (weathers[i].indexOf(THUNDERSTORMS) != -1) {
					imgids[i] = R.drawable.w_thunderstorms;
					continue;
				}
				imgids[i] = R.drawable.w_rain;
				continue;
			} else if (weathers[i].indexOf(SNOW) != -1) {
				imgids[i] = R.drawable.w_snow;
				continue;
			} else if (weathers[i].indexOf(HAZE) != -1) {
				imgids[i] = R.drawable.w_haze;
				continue;
			} else if (weathers[i].indexOf(FOG) != -1) {
				imgids[i] = R.drawable.w_fog;
				continue;
			} else if (weathers[i].indexOf(CLEAR) != -1) {
				imgids[i] = R.drawable.w_clear;
				continue;
			}

		}
		return imgids;
	}

	/**
	 * 方法太笨拙了。
	 * 
	 * @param day_of_week
	 * @return 当天周几
	 */
	public int matchDayofWeek(String day_of_week) {

		if (day_of_week.equals("星期一")) {
			return 1;
		} else if (day_of_week.equals("星期二")) {
			return 2;
		} else if (day_of_week.equals("星期三")) {
			return 3;
		} else if (day_of_week.equals("星期四")) {
			return 4;
		} else if (day_of_week.equals("星期五")) {
			return 5;
		} else if (day_of_week.equals("星期六")) {
			return 6;
		} else {
			return 7;
		}

	}

	/**
	 * 
	 * 这个方法太弱智了。没有去考虑好的方法~
	 * 
	 * @return 星期的数组
	 */
	public String[] weekDays(int i) {
		String[] days = null;
		switch (i) {
		case 1:
			days = new String[] { "周二", "周三", "周四", "周五" };
			break;
		case 2:
			days = new String[] { "周三", "周四", "周五", "周六" };
			break;
		case 3:
			days = new String[] { "周四", "周五", "周六", "周日" };
			break;
		case 4:
			days = new String[] { "周五", "周六", "周日", "周一" };
			break;
		case 5:
			days = new String[] { "周六", "周日", "周一", "周二" };
			break;
		case 6:
			days = new String[] { "周日", "周一", "周二", "周三" };
		case 7:
			days = new String[] { "周一", "周二", "周三", "周四" };
			break;

		}
		return days;
	}

}
