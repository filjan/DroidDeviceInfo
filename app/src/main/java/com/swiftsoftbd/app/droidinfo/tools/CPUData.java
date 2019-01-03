package com.swiftsoftbd.app.droidinfo.tools;

import android.os.Build;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class CPUData {

    public String GetArchitecture()
    {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // on newer Android versions, we'll return only the most important Abi version
            return Build.SUPPORTED_ABIS[0];
        }
        else {
            // on pre-Lollip versions, we got only one Abi
            return Build.CPU_ABI;
        }
    }

    public String GetCPUCores()
    {
        return (new File("/sys/devices/system/cpu/"))
                .listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return Pattern.matches("cpu[0-9]+", f.getName());
                    }
                }).length + "";
    }

    public String Frequencies()
    {
        StringBuffer sb = new StringBuffer();

        sb.append("Frequencies : ").append(getCPUFreqMHz("Min")).append(" MHz - ").append(getCPUFreqMHz("Max")).append(" MHz\n");
        if (new File("/proc/cpuinfo").exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(new File("/proc/cpuinfo")));
                String aLine;
                while ((aLine = br.readLine()) != null) {
                    sb.append(aLine + "\n");
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

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
}
