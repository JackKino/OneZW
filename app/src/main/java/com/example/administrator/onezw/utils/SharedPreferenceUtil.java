package com.example.administrator.onezw.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.administrator.onezw.OneZWApplication;
import com.example.administrator.onezw.config.Constant;
/**
 * 封装SharedPreference的工具类
 *
 */
public class SharedPreferenceUtil {
	 private static SharedPreferences sp;
	 
	public static void putString(String key, String value) {
		// 声明并获取一个SharedPreference对象，模式为私有模式
		//写入数据
		SharedPreferences sp = OneZWApplication.getContext().getSharedPreferences(Constant.APP, Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
	}

	public static String getString(String key) {
		//读取数据
		SharedPreferences sp = OneZWApplication.getContext().getSharedPreferences(Constant.APP, Context.MODE_PRIVATE);
		String values = sp.getString(key, "");
		return values;
	}
	
	 public static void putInt( String key, int value) {
		 sp =  OneZWApplication.getContext().getSharedPreferences(Constant.APP, Context.MODE_PRIVATE);
		 sp.edit().putInt(key, value).commit();
	    }

	    public static int getInt(String key) {
	    	sp =  OneZWApplication.getContext().getSharedPreferences(Constant.APP, Context.MODE_PRIVATE);
	        return sp.getInt(key, -1);
	    }

	    public static void putBoolean(Context context, String xmlName, String key, boolean value) {
	    	sp = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
	    	sp.edit().putBoolean(key, value).commit();
	    }

	    public static boolean getBoolean(Context context, String xmlName, String key) {
	    	sp = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
	        return sp.getBoolean(key, false);
	    }

	    public static void putFloat(Context context, String xmlName, String key, float value) {
	    	sp = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
	    	sp.edit().putFloat(key, value).commit();
	    }

	    public static float getFloat(Context context, String xmlName, String key) {
	    	sp = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
	        return sp.getFloat(key, -1);
	    }

	    public static void putLong(Context context, String xmlName, String key, long value) {
	    	sp = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
	    	sp.edit().putLong(key, value).commit();
	    }

	    public static long getLong(Context context, String xmlName, String key) {
	    	sp = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
	        return sp.getLong(key, -1);
	    }

}
