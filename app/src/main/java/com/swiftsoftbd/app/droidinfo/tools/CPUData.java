package com.swiftsoftbd.app.droidinfo.tools;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CPUData {
    public Map<String,String> processorMap = new HashMap<String,String>();

    public String GetArchitecture() {
        String returnValue = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // on newer Android versions, we'll return only the most important Abi version
            returnValue = Build.SUPPORTED_ABIS[0];
        } else {
            // on pre-Lollip versions, we got only one Abi
            returnValue = Build.CPU_ABI;
        }

        return returnValue;
    }

    public String GetCPUCores() {
        String returnValue = "";
        if (processorMap.containsKey("cpu cores"))
        {
            returnValue = processorMap.get("cpu cores");
        }

        return returnValue;    }

    public String getCPUFreqMHz() {

        String returnValue = "";
        if (processorMap.containsKey("cpu MHz"))
        {
            //returnValue = processorMap.get("cpu MHz");
            returnValue = String.format("%.02f Mhz", Double.parseDouble(processorMap.get("cpu MHz"))/1000f);
        }

        return returnValue;
    }

    public void GetProcessorInfo() {
        StringBuffer sb = new StringBuffer();

        if (new File("/proc/cpuinfo").exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(new File("/proc/cpuinfo")));
                String aLine;
                while ((aLine = br.readLine()) != null) {
                    aLine = aLine.replace("\t", "");
                    String[] values = aLine.split(":");
                    if (values.length > 1) {
                        processorMap.put(values[0], values[1]);
                    }
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //return sb.toString();
    }


    public String GetProcessorName(Context context)
    {
        String returnValue = "";
        if (processorMap.containsKey("model name"))
        {
            returnValue = processorMap.get("model name");
        }

        return returnValue;
    }

    public String GetGovernor() {
        StringBuffer sb = new StringBuffer();

        //String file = "/proc/cpuinfo";  // Gets most cpu info (but not the governor)
        String file = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";  // Gets governor

        java.io.RandomAccessFile reader = null;
        try {
            reader = new java.io.RandomAccessFile("/sys/bus/cpu/devices/cpu0/cpufreq/cpuinfo_cur_freq", "r");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (new File(file).exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(new File(file)));
                String aLine;
                while ((aLine = br.readLine()) != null)
                    sb.append(aLine + "\n");

                if (br != null)
                    br.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
