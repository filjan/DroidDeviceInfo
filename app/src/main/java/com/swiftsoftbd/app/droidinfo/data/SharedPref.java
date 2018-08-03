package com.swiftsoftbd.app.droidinfo.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

	/*
	 * cpu
	 */
	
	public static void setCPUData(Context ctx, String s){
		setStringPref("cpu_",s,ctx);
	}
	
	public static String getCPUData(Context ctx){
		return getStringPref("cpu_", "", ctx);
	}
	
	/*
	 * device
	 */
	
	public static void setDeviceData(Context ctx, String s){
		setStringPref("device_",s,ctx);
	}
	
	public static String getDeviceData(Context ctx){
		return getStringPref("device_", "", ctx);
	}
	
	
	/*
	 * System
	 */
	public static void setSystemData(Context ctx, String s){
		setStringPref("system_",s,ctx);
	}
	
	public static String getSystemData(Context ctx){
		return getStringPref("system_", "", ctx);
	}

	/*
    * Camera
    */
	public static void setCameraData(Context ctx, String s){ setStringPref("camera_",s,ctx); }

	public static String getCameraData(Context ctx){ return getStringPref("camera_", "", ctx);}

	/*
	 * Batery
	 */
	public static void setBateryData(Context ctx, String s){
		setStringPref("battery_",s,ctx);
	}
	
	public static String getBateryData(Context ctx){
		return getStringPref("battery_", "", ctx);
	}
	
	/*
	 * Support
	 */
	public static void setSensorData(Context ctx, String s){
		setStringPref("support_",s,ctx);
	}
	
	public static String getSensorData(Context ctx){
		return getStringPref("support_", "", ctx);
	}

	/*
		* Wifi
	 */
	public static void setWifiData(Context ctx, String s){
		setStringPref("wifi_",s,ctx);
	}

	public static String getWifiData(Context ctx){
		return getStringPref("wifi_", "", ctx);
	}
	/**
	 * Universal shared preference
	 * for string
	 */
	public static String getStringPref(String key_val, String def_val, Context context) {
		String stringValue;
		SharedPreferences pref = context.getSharedPreferences("pref_"+key_val,context.MODE_PRIVATE);

		stringValue = pref.getString(key_val, def_val);

		if (stringValue == null)
		{
			stringValue = "";
		}
		return pref.getString(key_val, def_val);
	}
	
	public static void setStringPref(String key_val, String val, Context context) {
		SharedPreferences pref = context.getSharedPreferences("pref_"+key_val,context.MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = pref.edit();
		prefEditor.clear();
		prefEditor.putString(key_val, val);
		prefEditor.commit();
	}
	
	/**
	 * Universal shared preference
	 * for integer
	 */
	public static int getIntPref(String key_val, int def_val, Context context) {
		SharedPreferences pref = context.getSharedPreferences("pref_"+key_val, context.MODE_PRIVATE);
		return pref.getInt(key_val, def_val);
	}
	
	public static void setIntPref(String key_val, int val, Context context) {
		SharedPreferences pref = context.getSharedPreferences("pref_"+key_val, context.MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = pref.edit();
		prefEditor.clear();
		prefEditor.putInt(key_val, val);
		prefEditor.commit();
	}
	
	/**
	 * Universal shared preference
	 * for boolean
	 */
	public static boolean getBooleanPref(String key_val, boolean def_val, Context context) {
		SharedPreferences pref = context.getSharedPreferences("pref_"+key_val,context.MODE_PRIVATE);
		return pref.getBoolean(key_val, def_val);
	}
	
	public static void setBooleanPref(String key_val, boolean val, Context context) {
		SharedPreferences pref = context.getSharedPreferences("pref_"+key_val,context.MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = pref.edit();
		prefEditor.clear();
		prefEditor.putBoolean(key_val, val);
		prefEditor.commit();
	}
	
}
