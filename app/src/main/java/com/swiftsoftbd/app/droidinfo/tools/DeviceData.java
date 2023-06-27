package com.swiftsoftbd.app.droidinfo.tools;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.hardware.SensorManager;
import android.hardware.Sensor;

import static android.content.Context.SENSOR_SERVICE;
import static com.swiftsoftbd.app.droidinfo.tools.MemoryUtils.*;

public class DeviceData {

    // get the model of the phone
    public String GetModel()
    {
        return Build.MODEL + " (" + Build.PRODUCT + ")";
    }

    // get the manufacturer of the phone
    public String GetManufacturer()
    {
        return Build.MANUFACTURER;
    }

    public String GetBoard()
    {
        return Build.BOARD;
    }

    public String GetBootLoader()
    {
        return Build.BOOTLOADER;
    }

    public String GetSerial()
    {
        return Build.SERIAL;
    }

    public String GetRadio() {
        String radioVersion = "";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                radioVersion = Build.getRadioVersion();
                if (radioVersion.isEmpty()) {
                    radioVersion = "Unavailable";
                }
            } else {
                radioVersion = "Unavailable";
            }
        } catch (Exception ex) {
            radioVersion = "Unavailable";
        }


        return radioVersion;
    }

    public String GetTotalRAM()
    {
        return getTotalRam();
    }

    public String GetAvailableRAM(Context context)
    {
        return getAvailableRam(context);
    }

    public String GetNetworkType(Context context)
    {
        return getNetworkType(context);
    }

    public String GetPhoneType(Context context)
    {
        return getPhoneType(context);
    }

    public String GetTotalStorage(Context context)
    {
        return Formatter.formatFileSize(context,MemoryUtils.getTotalInternalMemorySize());
    }

    public String GetUsageStorage(Context context)
    {
        return Formatter.formatFileSize(context,MemoryUtils.getFreeInternalMemorySize());
    }

    public String GetAccelerometerName(Context context)
    {
        SensorManager mSensorManager;
        Sensor mAccelerometer;

        mSensorManager = (SensorManager)context.getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (mAccelerometer != null) {
            return mAccelerometer.getName();
        }
        else {
            return "unavailable";
        }

    }

    public String GetGyroscopeName(Context context)
    {
        SensorManager mSensorManager;
        Sensor mGyroscope;

        mSensorManager = (SensorManager)context.getSystemService(SENSOR_SERVICE);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if (mGyroscope != null) {
            return mGyroscope.getName();
        }
        else {
            return "unavailable";
        }

    }

    public String GetMagnetometerName(Context context)
    {
        SensorManager mSensorManager;
        Sensor mMagnetometer;

        mSensorManager = (SensorManager)context.getSystemService(SENSOR_SERVICE);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if (mMagnetometer != null) {
            return mMagnetometer.getName();
        }
        else {
            return "unavailable";
        }

    }

    private String getPhoneType(Context context) {
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

    private String getNetworkType(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
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
        }
    }
}
