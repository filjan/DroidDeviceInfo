package com.swiftsoftbd.app.droidinfo.tools;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import androidx.core.app.ActivityCompat;

import com.swiftsoftbd.app.droidinfo.data.SharedPref;
import com.swiftsoftbd.app.droidinfo.model.TheInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class LoaderData {
	@SuppressLint("StaticFieldLeak")
	private static Context context;
	@SuppressLint("StaticFieldLeak")
	private static Activity activity;
	PackageManager pm;
	SensorManager mSensorManager;

	public LoaderData(Activity activity) {
		super();
		this.context = activity;
		this.activity = activity;
		pm = context.getPackageManager();
		mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

	}


	public void loadCpuInfo() {
		String s = "";
		StringBuilder sb = new StringBuilder();
		sb.append("Architecture : ").append(Build.CPU_ABI).append("\n");
		sb.append("CPU Cores : ").append(GetNumberOfCores()).append(" cores").append("\n");
		sb.append("Frequencies : ").append(getCPUFreqMHz("Min")).append(" MHz - ").append(getCPUFreqMHz("Max")).append(" MHz\n");
		if (new File("/proc/cpuinfo").exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(new File("/proc/cpuinfo")));
				String aLine;
				while ((aLine = br.readLine()) != null) {
					sb.append(aLine).append("\n");
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		s = sb.toString();
		SharedPref.setCPUData(context, s);
	}

	public void loadDeviceInfo() {
		String s = "";
		s = s + "Model : " + Build.MODEL + " (" + Build.PRODUCT + ")" + "\n";
		s = s + "Manufacturer : " + Build.MANUFACTURER + "\n";
		s = s + "Board : " + Build.BOARD + "\n";
		s = s + "Bootloader : " + Build.BOOTLOADER + "\n";
		s = s + "Serial : " + Build.SERIAL + "\n";
		s = s + "Radio : " + Build.getRadioVersion() + "\n";

		s = s + "Total RAM : " + MemoryUtils.getTotalRam() + "\n";
		s = s + "Available RAM : " + MemoryUtils.getAvailableRam(context) + "\n";
		s = s + "Networks Type : " + getNetworkType(context) + "\n";
		s = s + "Phone Type :" + getPhoneType(context) + "\n";

		s = s + "-:-\n";// give separator
		s = s + "Internal Storage : \n";
		s = s + "# : Total (" + Formatter.formatFileSize(context, MemoryUtils.getTotalInternalMemorySize()) + ")\n";
		s = s + "# : Usage (" + Formatter.formatFileSize(context, MemoryUtils.getFreeInternalMemorySize()) + ")\n";
		// external
		String[] dir = MemoryUtils.getStorageDirectories();
		for (int j = 0; j < dir.length; j++) {
			if (!TextUtils.isEmpty(dir[j])) {
				File file = new File(dir[j]);
				if (file.exists() && file.length() > 0) {
					s = s + "-:-\n";// give separator
					s = s + "External Storage : \n";
					s = s + "# : Total (" + Formatter.formatFileSize(context, MemoryUtils.getTotalExternalMemorySize(file)) + ")\n";
					s = s + "# : Usage (" + Formatter.formatFileSize(context, MemoryUtils.getFreeExternalMemorySize(file)) + ")\n";
				}
			}
		}
		SharedPref.setDeviceData(context, s);
	}


	public void loadSystemInfo() {
		Context applicationContext = context.getApplicationContext();
		context = applicationContext != null ? applicationContext : context;

		String s = "";
		s = s + "System Information : \n";// give separator
		s = s + "-:-\n";// give separator
		s = s + "Android Version : " + Build.VERSION.RELEASE + "\n";
		s = s + "API Level : " + Build.VERSION.SDK_INT + "\n";
		s = s + "Android ID : " + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID) + "\n";
		s = s + "Kernel Architecture : " + System.getProperty("os.arch") + "\n";
		s = s + "Build ID : " + Build.DISPLAY + "\n";
		s = s + "Root Acces : " + new RootChecker().isDeviceRooted() + "\n";
		s = s + "Kernel : " + System.getProperty("os.version") + "\n";
		s = s + "Java VM : " + getJavaRuntime() + "\n";
		s = s + "Language : " + getLanguage() + "\n";
		s = s + "Time Zone : " + getTimezone() + "\n";

		long seconds = SystemClock.elapsedRealtime() / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		long days = hours / 24;
		String time = days + ":" + hours % 24 + ":" + minutes % 60 + ":" + seconds % 60;

		s = s + "Time Since Reboot : " + days + " days, " + hours % 24 + " hours, " + minutes % 60 + " minutes, " + seconds % 60 + " seconds\n\n";

		s = s + "Screen Information : \n";// give separator
		s = s + "-:-\n";// give separator
		s = s + "Density : " + getScreenDensity() + "\n";
		s = s + "Screen Resolution : " + getScreenResolution() + "\n";
		s = s + "Refresh Rate : " + getDisplayRefresh() + " Hz" + "\n";
		SharedPref.setSystemData(context, s);
	}

	public void loadCameraInfo() {

		String s = "";
		Intent intent;
//		intent = activity.registerReceiver();
		intent = activity.registerReceiver(null, new IntentFilter((Intent.ACTION_CAMERA_BUTTON)));

	}

	public void loadBateryInfo() {
		Context applicationContext = context.getApplicationContext();
		context = applicationContext != null ? applicationContext : context;
		String s = "";
		Intent intent = activity.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		assert intent != null;
		boolean isPresent = intent.getBooleanExtra("present", false);
		String technology = intent.getStringExtra("technology");
		int plugged = intent.getIntExtra("plugged", -1);
		int scale = intent.getIntExtra("scale", -1);
		int health = intent.getIntExtra("health", 0);
		int status = intent.getIntExtra("status", 0);
		int rawlevel = intent.getIntExtra("level", -1);
		int voltage = intent.getIntExtra("voltage", 0);
		int temperature = (intent.getIntExtra("temperature", 0) / 10);
		int level = 0;

		Bundle bundle = intent.getExtras();

		BatteryUtils batteryUtils = new BatteryUtils();

		if (isPresent) {
			if (rawlevel >= 0 && scale > 0) {
				level = (rawlevel * 100) / scale;
			}

			s = s + "Battery Level: " + level + "%\n";
			s = s + "Technology: " + (technology = technology == "" ? "Not present" : technology) + "\n";
			s = s + "Plugged: " + batteryUtils.getPlugTypeString(context) + "\n";
			s = s + "Health: " + batteryUtils.getHealthString(context) + "\n";
			s = s + "Status: " + batteryUtils.getStatusString(context) + "\n";
			s = s + "Voltage: " + voltage + " mV\n";
			s = s + "Temperature: " + temperature + Character.toString((char) 176) + " C\n";
		} else {
			s = "<battery not present>";
		}
		SharedPref.setBateryData(context, s);
	}


	public void loadSupportInfo() {

		String s = "";
		s = s + "Bluetooth : " + SensorUtil.getBluetoothSupport(pm) + "\n";
		s = s + "WiFi : " + SensorUtil.getWiFiSupport(pm) + "\n";
		s = s + "GPS : " + SensorUtil.getGPSSupport(pm) + "\n";
		s = s + "Live Wallpapers : " + SensorUtil.getLiveWallpapersSupport(pm) + "\n";
		s = s + "Microphone : " + SensorUtil.getLiveMicrophoneSupport(pm) + "\n";
		s = s + "-:-\n";// give separator
		s = s + "Accelerometer : " + SensorUtil.getLiveAcceleratorMeterSupport(pm) + "\n";
		s = s + "Barometer : " + SensorUtil.getBarometerSupport(pm) + "\n";
		s = s + "Compass : " + SensorUtil.getCompassSupport(pm) + "\n";
		s = s + "Gyroscope : " + SensorUtil.getGyscopeSupport(pm) + "\n";
		s = s + "Light : " + SensorUtil.getLightsSupport(pm) + "\n";
		s = s + "Magnetic Field : " + SensorUtil.getMagneticFieldSupport(mSensorManager) + "\n";
		s = s + "Linear Accel. : " + SensorUtil.getLinearAccelerationSupport(mSensorManager) + "\n";
		s = s + "Orientation : " + SensorUtil.getOrientationSupport(mSensorManager) + "\n";
		s = s + "Pressure : " + SensorUtil.getPressureSupport(mSensorManager) + "\n";
		s = s + "Proximity : " + SensorUtil.getProximitySupport(pm) + "\n";
		s = s + "NFC : " + SensorUtil.getNFCSupport(pm) + "\n";
		s = s + "Rotation Vector : " + SensorUtil.getRotationVectorSupport(mSensorManager) + "\n";
		s = s + "Temperature : " + SensorUtil.getTemperatureSupport(mSensorManager) + "\n";
		s = s + "Gravity : " + SensorUtil.getGravitySupport(mSensorManager) + "\n";
		SharedPref.setSensorData(context, s);
	}

	public void loadWifiInfo() {
		String s = "";

		s = s + "Wifi Information : \n";// give separator
		s = s + "-:-\n";// give separator
		s = s + "Enabled : " + WifiData.IsEnabled(context) + "\n";
		s = s + "Data State : test\n";
		s = s + "Handshake State : test\n";
		s = s + "SSID : " + WifiData.GetSSID(context) + "\n";
		s = s + "BSSID : " + WifiData.GetBSSID(context) + "\n";
		s = s + "Vendor : test\n";
		s = s + "Channel : test\n";
		s = s + "IP Address : " + WifiData.GetIPAddress(context) + "\n";
		s = s + "Subnet Mask : test\n";
		s = s + "IPv6 Addresses : " + WifiData.GetIPv6(context) + "\n";
		s = s + "MAC : " + WifiData.GetMacAddress(context) + "\n";
		s = s + "Speed : " + WifiData.GetWifiSpeed(context) + "\n";
		s = s + "Signal Strength : " + WifiData.GetSignalStrength(context) + "\n";
		s = s + "Received Since Reboot : " + WifiData.GetReceivedSinceReboot() + "\n";
		s = s + "Sent Since Reboot : " + WifiData.GetSentSinceReboot() + "\n";

		SharedPref.setWifiData(context, s);
	}

	public static ArrayList<TheInfo> getArrList(String string) {
		ArrayList<TheInfo> infos = new ArrayList<TheInfo>();
		String[] arr = StrFormatter.splitEveryLIne(string);
		for (String s : arr) {
			if (s != null) {
				if (!s.trim().equals("")) {
					String[] arr2 = StrFormatter.splitWitDoubleDot(s);
					if (arr2 != null) {
						if (arr2.length > 1 && arr2[1].trim().length() < 50) {
							if (arr2[0].trim().toLowerCase().equals("processor")) {
								infos.add(new TheInfo("-", "-"));
							}
							infos.add(new TheInfo(StrFormatter.getFormattedName(arr2[0].trim()), arr2[1].trim()));
						}
					} else {
						infos.add(new TheInfo("", "")); // if null create a blank entry.
					}
				}
			}
		}
		return infos;
	}

	private static String getScreenResolution() {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;

		return width + " x " + height + " pixels";
	}


	private String getJavaVM() {
		final String SELECT_RUNTIME_PROPERTY = "persist.sys.dalvik.vm.lib";
		final String LIB_DALVIK = "libdvm.so";
		final String LIB_ART = "libart.so";
		final String LIB_ART_D = "libartd.so";

		try {
			Class<?> systemProperties = Class.forName("android.os.SystemProperties");
			try {
				Method get = systemProperties.getMethod("get", String.class, String.class);
				if (get == null) {
					return "<unknown>";
				}
				try {
					final String value = (String) get.invoke(systemProperties, SELECT_RUNTIME_PROPERTY, "Dalvik");
					if (LIB_DALVIK.equals(value)) {
						return "Dalvik";
					} else if (LIB_ART.equals(value)) {
						return "ART";
					} else if (LIB_ART_D.equals(value)) {
						return "ART debug build";
					}

					return value;
				} catch (IllegalAccessException e) {
					return "IllegalAccessException";
				} catch (IllegalArgumentException e) {
					return "IllegalArgumentException";
				} catch (InvocationTargetException e) {
					return "InvocationTargetException";
				}
			} catch (NoSuchMethodException e) {
				return "SystemProperties.get(String key, String def) method is not found";
			}
		} catch (ClassNotFoundException e) {
			return "SystemProperties class is not found";
		}
	}

	public String getNetworkType(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return "Unavailable";
		}
		switch (tm.getNetworkType()) {
			case TelephonyManager.NETWORK_TYPE_1xRTT:
				return "1xRTT";

			case TelephonyManager.NETWORK_TYPE_CDMA:
				return "CDMA";

			case TelephonyManager.NETWORK_TYPE_EDGE:
				return "EDGE";

			case TelephonyManager.NETWORK_TYPE_EHRPD:
				return "eHRPD";

			case TelephonyManager.NETWORK_TYPE_EVDO_0:
				return "EVDO revision 0";

			case TelephonyManager.NETWORK_TYPE_EVDO_A:
				return "EVDO revision A";

			case TelephonyManager.NETWORK_TYPE_EVDO_B:
				return "EVDO revision B";

			case TelephonyManager.NETWORK_TYPE_GPRS:
				return "GPRS";

			case TelephonyManager.NETWORK_TYPE_GSM:
				return "GSM";

			case TelephonyManager.NETWORK_TYPE_HSDPA:
				return "HSDPA";

			case TelephonyManager.NETWORK_TYPE_HSPA:
				return "HSPA";

			case TelephonyManager.NETWORK_TYPE_HSPAP:
				return "HSPAP";

			case TelephonyManager.NETWORK_TYPE_HSUPA:
				return "HSUPA";

			case TelephonyManager.NETWORK_TYPE_IDEN:
				return "iDen";

			case TelephonyManager.NETWORK_TYPE_IWLAN:
				return "IWLAN";

			case TelephonyManager.NETWORK_TYPE_LTE:
				return "LTE";

			case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
				return "TD_SCDMA";

			case TelephonyManager.NETWORK_TYPE_UMTS:
				return "UMTS";

			case TelephonyManager.NETWORK_TYPE_UNKNOWN:
				return "Unknown";

			default:
				return "Unavailable";
			case TelephonyManager.NETWORK_TYPE_NR:
				break;
		}
		return "Unavailable";
	}

	public String getPhoneType(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		int phoneType=tm.getPhoneType();

		switch (tm.getPhoneType()) {
			case TelephonyManager.PHONE_TYPE_CDMA:
				return "CDMA";

			case TelephonyManager.PHONE_TYPE_GSM:
				return "GSM";

			case TelephonyManager.PHONE_TYPE_SIP:
				return "SIP";

			case TelephonyManager.PHONE_TYPE_NONE:
				return "None";
			default:
				return "Unavailable";
		}
	}

	public int GetNumberOfCores() {
		return (new File("/sys/devices/system/cpu/"))
				.listFiles(new FileFilter() {
					@Override
					public boolean accept(File f) {
						return Pattern.matches("cpu[0-9]+", f.getName());
					}
				}).length;
	}

	/**
	 * Get max cpu rate.
	 *
	 * This works by examining the list of CPU frequencies in the pseudo file
	 * "/sys/devices/system/cpu/cpu0/cpufreq/stats/time_in_state" and how much time has been spent
	 * in each. It finds the highest non-zero time and assumes that is the maximum frequency (note
	 * that sometimes frequencies higher than that which was designed can be reported.) So it is not
	 * impossible that this method will return an incorrect CPU frequency.
	 *
	 * Also note that (obviously) this will not reflect different CPU cores with different
	 * maximum speeds.
	 *
	 * @return cpu frequency in MHz
	 */
	public int getCPUFreqMHz(String typeFreq) {

		int maxFreq = -1;
		try {

			java.io.RandomAccessFile reader = null;
			if (typeFreq == "Max" ) {
				reader = new java.io.RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq", "r");
			}
			else if (typeFreq == "Min")
			{
				reader = new java.io.RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq", "r");
			}
			//RandomAccessFile reader = new RandomAccessFile( "/sys/devices/system/cpu/cpu0/cpufreq/stats/time_in_state", "r" );

			boolean done = false;
			while ( ! done ) {
				String line = reader.readLine();
				if ( null == line ) {
					done = true;
					break;
				}
				String[] splits = line.split( "\\s+" );
				assert ( splits.length == 2 );
				int timeInState = 0;
				if (splits.length == 2)
				{
					timeInState = Integer.parseInt( splits[1] );
				}
				else
				{
					timeInState = Integer.parseInt( splits[0] );
				}
				//int timeInState = Integer.parseInt( splits[1] );
				if ( timeInState > 0 ) {
					int freq = Integer.parseInt( splits[0] ) / 1000;
					if ( freq > maxFreq ) {
						maxFreq = freq;
					}
				}
			}

		} catch ( IOException ex ) {
			ex.printStackTrace();
		}

		return maxFreq;
	}

    private String getScreenDensity() {
        String densityValue = "";

		try {
			final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

			//((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
			int dpiClassification = displayMetrics.densityDpi;

			if (dpiClassification <= DisplayMetrics.DENSITY_LOW) {
				densityValue = "(ldpi) " + String.valueOf(dpiClassification);
			}
			else if (dpiClassification > DisplayMetrics.DENSITY_LOW  && dpiClassification <= DisplayMetrics.DENSITY_MEDIUM) {
				densityValue = "(mdpi) " + String.valueOf(dpiClassification);
			}
			else if (dpiClassification > DisplayMetrics.DENSITY_MEDIUM && dpiClassification <= DisplayMetrics.DENSITY_HIGH) {
				densityValue = "(hdpi) " + String.valueOf(dpiClassification);
			}
			else if (dpiClassification > DisplayMetrics.DENSITY_HIGH && dpiClassification <= DisplayMetrics.DENSITY_XHIGH) {
				densityValue = "(xhdpi) " + String.valueOf(dpiClassification);
			}
			else if (dpiClassification > DisplayMetrics.DENSITY_XHIGH && dpiClassification <= DisplayMetrics.DENSITY_XXHIGH) {
				densityValue = "(xxhdpi) " + String.valueOf(dpiClassification);
			}
			else if (dpiClassification > DisplayMetrics.DENSITY_XXHIGH && dpiClassification <= DisplayMetrics.DENSITY_XXXHIGH) {
				densityValue = "(xxxhdpi) " + String.valueOf(dpiClassification);
			}
			else
			{
				densityValue = "";
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
		}


        return densityValue;
    }

    public String getDisplayRefresh()
	{
		String refreshRate = "";

		try {
			final WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
			final Display display = wm.getDefaultDisplay();
			refreshRate = String.format("%.1f",display.getRefreshRate());


		}
		catch (Exception ex){
			ex.printStackTrace();
		}

		return refreshRate;
	}

	public String getJavaRuntime()
	{
		String runtimeType = "";

		try {
			runtimeType = System.getProperty("java.vm.version");

			runtimeType = getJavaVM() + " " + runtimeType;
		}
		catch (Exception ex){
			ex.printStackTrace();
		}


		return runtimeType;
	}

	public String getTimezone()
	{
		String timeZone = "";

		try {
			TimeZone tz = TimeZone.getDefault();
			timeZone = tz.getDisplayName(false, TimeZone.LONG);
		}
		catch (Exception ex){
			ex.printStackTrace();
		}

		return timeZone;
	}

	public String getLanguage()
	{
		String displayLanguage = "";

		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
				displayLanguage = context.getResources().getConfiguration().getLocales().get(0).getDisplayLanguage();
			} else {
				displayLanguage = context.getResources().getConfiguration().locale.getDisplayLanguage();
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
		}

		return displayLanguage;
	}
}
