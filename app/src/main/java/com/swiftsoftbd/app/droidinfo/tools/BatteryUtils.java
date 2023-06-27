package com.swiftsoftbd.app.droidinfo.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import java.text.DecimalFormat;

public class BatteryUtils {
	public String getBatteryLevel(Context context)
	{
		int rawlevel = 0;
		int scale = 0;

		Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		if (intent != null) {
			rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
			scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
		}

		int level = -1;
		if (rawlevel >= 0 && scale > 0) {
			level = (rawlevel * 100) / scale;
		}

		return level + "%";
	}

	public String getBatteryTechnology(Context context)
	{
		String tech = "";

		Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

		if (intent != null ) {
			tech = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
		}

		return tech;
	}

	public String getBatteryVoltage(Context context)
	{
		double decimalVoltage = 0.0;
		int  voltage = 0;
		int volt = 0;

		try
		{
			Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

			if (intent != null) {
				volt = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
			}
			IntentFilter batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

			Intent batteryIntent = context.registerReceiver(null, batteryIntentFilter);
			if (batteryIntent != null) {
				voltage = batteryIntent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
			}

			float fullVoltage = (float) (volt * 0.001);

			DecimalFormat decimalformat = new DecimalFormat("#.#");

			decimalVoltage = Double.parseDouble(decimalformat.format(fullVoltage));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return "Unknown";
		}


		return decimalVoltage + "" + " Volt";
	}

	public String getPlugTypeString(Context context) {
		String plugType = "Unknown";
		int plugged = 0;

		try
		{
			Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
			if (intent != null) {
				plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
			}
	}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return "Unknown";
		}

		switch (plugged) {
		case BatteryManager.BATTERY_PLUGGED_AC:
			plugType = "AC";
			break;
		case BatteryManager.BATTERY_PLUGGED_USB:
			plugType = "USB";
			break;
		case BatteryManager.BATTERY_PLUGGED_WIRELESS:
			plugType = "Wireless";
			break;
		case 0:
			plugType = "Unplugged";
			break;
		}

		return plugType;
	}

	public String getHealthString(Context context) {
		String healthString = "Unknown";
		int health = 0;

		try
		{
			Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

			if (intent != null) {
				health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		switch (health) {
		case BatteryManager.BATTERY_HEALTH_DEAD:
			healthString = "Dead";
			break;
		case BatteryManager.BATTERY_HEALTH_GOOD:
			healthString = "Good";
			break;
		case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
			healthString = "Over Voltage";
			break;
		case BatteryManager.BATTERY_HEALTH_OVERHEAT:
			healthString = "Over Heat";
			break;
		case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
			healthString = "Failure";
			break;
		}

		return healthString;
	}

	public String getStatusString(Context context) {
		String statusString = "Unknown";
		int status = 0;
		Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

		if (intent != null) {
			status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
		}

		switch (status) {
		case BatteryManager.BATTERY_STATUS_CHARGING:
			statusString = "Charging";
			break;
		case BatteryManager.BATTERY_STATUS_DISCHARGING:
			statusString = "Discharging";
			break;
		case BatteryManager.BATTERY_STATUS_FULL:
			statusString = "Full";
			break;
		case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
			statusString = "Not Charging";
			break;
		}

		return statusString;
	}

	public String getTemperature(Context context) {
		float temp = 0;
		String plugType = "Unknown";
		int temperature = 0;

		Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		if (intent != null) {
			temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
		}

		if (temperature > 0) {
			temp = ((float) temperature) / 10f;
		}
		return temp + "°C";
	}
}
