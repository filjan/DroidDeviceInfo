package com.swiftsoftbd.app.droidinfo.tools;

import android.os.Build;

public class DeviceData {
    public String GetModel()
    {
        return Build.MODEL + " (" + Build.PRODUCT + ")";
    }
}
