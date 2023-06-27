package com.swiftsoftbd.app.droidinfo.tools;

import android.content.Context;
import android.location.SettingInjectorService;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.TimeZone;

public class SystemData {
    public String getAndroidVersion(Context context) {
        return Build.VERSION.RELEASE;
    }

    public String getAPILevel(Context context) {
        return Build.VERSION.SDK_INT + "";
    }

    public String getAndroidID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public String getKernelArchitecture(Context context) {
        return System.getProperty("os.arch");
    }

    public String getBuildID(Context context) {
        return Build.DISPLAY;
    }

    public String getRootAccess(Context context) {
        return new RootChecker().isDeviceRooted();
    }

    public String getKernel(Context context) {
        return System.getProperty("os.version");
    }

    public String getJavaVM(Context context) {
        String runtimeType = "";

        try {
            runtimeType = System.getProperty("java.vm.version");

            runtimeType = getJavaVMSystem() + " " + runtimeType;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }


        return runtimeType;
    }

    public String getTimeZone()
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

    public String getLanguage(Context context)
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

    public String getSinceReboot(Context context) {
        long seconds = SystemClock.elapsedRealtime() / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        return days + " days, " + hours % 24 + " hours, " + minutes % 60 + " minutes, " + seconds % 60 + " seconds";
    }

    public String getDensity(Context context) {
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


        return densityValue;    }

    public String getScreenResolution(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);

        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        return width+" x "+height+" pixels";    }

    public String getRefreshRate(Context context) {
        String refreshRate = "";

        try {
            final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            final Display display = wm.getDefaultDisplay();
            refreshRate = String.format("%.1f",display.getRefreshRate());


        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return refreshRate + " Hz" ;
    }

    private String getJavaVMSystem() {
        final String SELECT_RUNTIME_PROPERTY = "persist.sys.dalvik.vm.lib";
        final String LIB_DALVIK = "libdvm.so";
        final String LIB_ART = "libart.so";
        final String LIB_ART_D = "libartd.so";

        try {
            Class<?> systemProperties = Class.forName("android.os.SystemProperties");
            try {
                Method get = systemProperties.getMethod("get",String.class, String.class);
                if (get == null) {
                    return "<unknown>";
                }
                try {
                    final String value = (String)get.invoke(systemProperties, SELECT_RUNTIME_PROPERTY,"Dalvik");
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
}
