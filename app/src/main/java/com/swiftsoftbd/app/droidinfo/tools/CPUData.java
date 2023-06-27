package com.swiftsoftbd.app.droidinfo.tools;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.util.Log;

import com.swiftsoftbd.app.droidinfo.data.SharedPref;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.sql.DriverManager.println;

public class CPUData {
//    public Map<String,String> processorMap = new HashMap<String,String>();
    public java.util.ArrayList<DroidValuePair> processorInfo = new java.util.ArrayList<DroidValuePair>();
    public HashMap<String,String> processorMap = new HashMap<String,String>();


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
        int numcpu;
        numcpu = (new File("/sys/devices/system/cpu/"))
                .listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return Pattern.matches("cpu[0-9]+", f.getName());
                    }
                }).length;

        return String.valueOf(numcpu);
    }

    @SuppressLint("DefaultLocale")
    public String getMinCPUFreqMHz() {

        String returnValue = "";
        if (processorMap.containsKey("cpu MHz"))
        {
            returnValue = String.format("%.02f Mhz", Double.parseDouble(processorMap.get("cpu MHz"))/1000f);
        }

        return returnValue;
    }

    public String getCPUFreqMHz() {
        String returnValue = "";
        int maxFreq = -1;
        int minFreq = -1;

        //try {

            java.io.RandomAccessFile maxReader = null;
            java.io.RandomAccessFile minReader = null;
            //maxReader = new java.io.RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq", "r");
            //minReader = new java.io.RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq", "r");

            //maxFreq = readFreq(maxReader);
            //minFreq = readFreq(minReader);
        //} catch ( IOException ex ) {
        //    ex.printStackTrace();
        //}

        //if (maxFreq > 0 && minFreq > 0)
        //{
        //    returnValue = maxFreq + " MHz - " + minFreq + " MHz";
        //}
        //else
        //{
        //    returnValue = getMinCPUFreqMHz();
        //}

        return returnValue;
    }

    public int readFreq(java.io.RandomAccessFile fileReader) {
        int maxFreq = -1;

        boolean done = false;
        try {
            while (!done) {
                String line = fileReader.readLine();
                if (null == line) {
                    done = true;
                    break;
                }
                String[] splits = line.split("\\s+");
                assert (splits.length == 2);
                int timeInState = 0;
                if (splits.length == 2) {
                    timeInState = Integer.parseInt(splits[1]);
                } else {
                    timeInState = Integer.parseInt(splits[0]);
                }
                //int timeInState = Integer.parseInt( splits[1] );
                if (timeInState > 0) {
                    int freq = Integer.parseInt(splits[0]) / 1000;
                    if (freq > maxFreq) {
                        maxFreq = freq;
                    }
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return maxFreq;
    }

    private static int readSystemFileAsInt(final String pSystemFile) {
        InputStream in = null;
        try {
            final Process process = new ProcessBuilder(new String[] { "/system/bin/cat", pSystemFile }).start();

            in = process.getInputStream();
            final String content = readFully(in);
            return Integer.parseInt(content);
        } catch (final Exception e) {
         //   throw new Exception(e);
            return 0;
        }
    }

    private static String readFully(final InputStream pInputStream) throws IOException {
        final StringBuilder sb = new StringBuilder();
        final Scanner sc = new Scanner(pInputStream);
        while(sc.hasNextLine()) {
            sb.append(sc.nextLine());
        }
        return sb.toString();
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
                    DroidValuePair pair = new DroidValuePair();
                    if (values.length > 1) {
                        pair.setFirstValue(values[0]);
                        pair.setSecondValue(values[1]);
                        processorInfo.add(pair);
                    }
                    else
                    {
                        pair.setFirstValue("");
                        pair.setSecondValue("");
                        processorInfo.add(pair);
                    }
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String GetProcessorMfg(Context context)
    {
        String procMfg = "";

        procMfg = Build.MANUFACTURER;

        if (procMfg == "qcom")
        {
            procMfg = "Qualcomm";
        }

        return procMfg;
    }

    public String GetProcessorModel(Context context)
    {
        String procName = "";

        switch (Build.MODEL)
        {
            case "MSM7225":
                procName = "Snapdragon S1";
                break;
            case "MSM7625":
                procName = "Snapdragon S1";
            case "MSM7227":
                procName = "Snapdragon S1";
            case "MSM7627":
                procName = "Snapdragon S1";
            case "MSM7225A":
                procName = "Snapdragon S1";
            case "MSM7625A":
                procName = "Snapdragon S1";
            case "MSM7227A":
                procName = "Snapdragon S1";
            case "MSM7627A":
                procName = "Snapdragon S1";
            case "MSM7225AB":
                procName = "Snapdragon S1";
            case "QSD8250":
                procName = "Snapdragon S1";
            case "QSD8650":
                procName = "Snapdragon S1";
            case "MSM7230":
                procName = "Snapdragon S2";
            case "MSM7630":
                procName = "Snapdragon S2";
            case "APQ8055":
                procName = "Snapdragon S2";
            case "MSM8255":
                procName = "Snapdragon S2";
            case "MSM8655":
                procName = "Snapdragon S2";
            case "MSM8255T":
                procName = "Snapdragon S2";
            case "MSM8655T":
                procName = "Snapdragon S2";
            case "APQ8060":
                procName = "Snapdragon S3";
            case "MSM8260":
                procName = "Snapdragon S3";
            case "MSM8660":
                procName = "Snapdragon S3";
            case "MSM8225":
                procName = "Snapdragon S4 Play";
            case "MSM8625":
                procName = "Snapdragon S4 Play";
            case "MSM8227":
                procName = "Snapdragon S4 Plus";
            case "MSM8627":
                procName = "Snapdragon S4 Plus";
            case "APQ8030":
                procName = "Snapdragon S4 Plus";
            case "MSM8230":
                procName = "Snapdragon S4 Plus";
            case "MSM8630":
                procName = "Snapdragon S4 Plus";
            case "MSM8930":
                procName = "Snapdragon S4 Plus";
            case "APQ8060A":
                procName = "Snapdragon S4 Plus";
            case "MSM8260A":
                procName = "Snapdragon S4 Plus";
            case "MSM8660A":
                procName = "Snapdragon S4 Plus";
            case "MSM8960":
                procName = "Snapdragon S4 Plus";
            case "MSM8260A Pro":
                procName = "Snapdragon S4 Pro";
            case "MSM8960T":
                procName = "Snapdragon S4 Pro";
            case "MSM8960T Pro":
                procName = "Snapdragon S4 Pro";
            case "MSM8960AB":
                procName = "Snapdragon S4 Pro";
            case "MSM8960DT":
                procName = "Snapdragon S4 Pro";
            case "APQ8064":
                procName = "Snapdragon S4 Pro";
            case "MSM8225Q":
                procName = "Snapdragon 200";
            case "MSM8625Q":
                procName = "Snapdragon 200";
            case "MSM8210":
                procName = "Snapdragon 200";
            case "MSM8610":
                procName = "Snapdragon 200";
            case "MSM8212":
                procName = "Snapdragon 200";
            case "MSM8612":
                procName = "Snapdragon 200";
            case "MSM8905":
                procName = "Qualcomm 205";
            case "MSM8208":
                procName = "Snapdragon 208";
            case "MSM8909":
                procName = "Snapdragon 210";
            case "MSM8909AA":
                procName = "Snapdragon 212";
            case "QM215":
                procName = "Qualcomm 215";
            case "APQ8026":
                procName = "Snapdragon 400";
            case "MSM8226":
                procName = "Snapdragon 400";
            case "MSM8626":
                procName = "Snapdragon 400";
            case "MSM8926":
                procName = "Snapdragon 400";
            case "APQ8028":
                procName = "Snapdragon 400";
            case "MSM8228":
                procName = "Snapdragon 400";
            case "MSM8628":
                procName = "Snapdragon 400";
            case "MSM8928":
                procName = "Snapdragon 400";
//            case "MSM8230":
//                procName = "Snapdragon 400";
//            case "MSM8630":
//                procName = "Snapdragon 400";
//            case "MSM8930":
//                procName = "Snapdragon 400";
            case "MSM8930AA":
                procName = "Snapdragon 400";
            case "APQ8030AB":
                procName = "Snapdragon 400";
            case "MSM8230AB":
                procName = "Snapdragon 400";
            case "MSM8630AB":
                procName = "Snapdragon 400";
            case "MSM8930AB":
                procName = "Snapdragon 400";
            case "APQ8016":
                procName = "Snapdragon 410";
            case "MSM8916":
                procName = "Snapdragon 410";
            case "MSM8916 v2":
                procName = "Snapdragon 412";
            case "MSM8929":
                procName = "Snapdragon 415";
            case "MSM8917":
                procName = "Snapdragon 425";
            case "MSM8920":
                procName = "Snapdragon 427";
            case "MSM8937":
                procName = "Snapdragon 430";
            case "MSM8940":
                procName = "Snapdragon 435";
            case "SDM429":
                procName = "Snapdragon 429";
            case "SDM439":
                procName = "Snapdragon 439";
            case "SDM450":
                procName = "Snapdragon 450";
            case "APQ8064-1AA":
                procName = "Snapdragon 600";
            case "APQ8064M":
                procName = "Snapdragon 600";
            case "APQ8064T":
                procName = "Snapdragon 600";
            case "APQ8064AB":
                procName = "Snapdragon 600";
            case "MSM8936":
                procName = "Snapdragon 610";
            case "MSM8939":
                procName = "Snapdragon 615";
            case "MSM8939 v2":
                procName = "Snapdragon 616";
            case "MSM8952":
                procName = "Snapdragon 617";
            case "MSM8953":
                procName = "Snapdragon 625";
            case "MSM8953 Pro":
                procName = "Snapdragon 626";
            case "MSM8956":
                procName = "Snapdragon 650";
            case "MSM8976":
                procName = "Snapdragon 652";
            case "MSM8976 Pro":
                procName = "Snapdragon 653";
            case "SDM630":
                procName = "Snapdragon 630";
            case "SDM636":
                procName = "Snapdragon 636";
            case "SDM660":
                procName = "Snapdragon 660";
            case "SDM632":
                procName = "Snapdragon 632";
            case "SM6125":
                procName = "Snapdragon 665";
            case "SDM670":
                procName = "Snapdragon 670";
            case "SM6150":
                procName = "Snapdragon 675";
            case "SDM710":
                procName = "Snapdragon 710";
            case "SDM712":
                procName = "Snapdragon 712";
            case "SM7125":
                procName = "Snapdragon 720G";
            case "SM7150-AA":
                procName = "Snapdragon 730";
            case "SM7150-AB":
                procName = "Snapdragon 730G";
            case "SM7250-AA":
                procName = "Snapdragon 765";
            case "SM7250-AB":
                procName = "Snapdragon 765G";
            case "APQ8074AA":
                procName = "Snapdragon 800";
            case "MSM8274AA":
                procName = "Snapdragon 800";
            case "MSM8674AA":
                procName = "Snapdragon 800";
            case "MSM8974AA":
                procName = "Snapdragon 800";
            case "MSM8974AA v3":
                procName = "Snapdragon 801";
            case "APQ8074AB v3":
                procName = "Snapdragon 801";
            case "MSM8274AB":
                procName = "Snapdragon 800";
            case "MSM8674AB v3":
                procName = "Snapdragon 801";
            case "MSM8974AB v3":
                procName = "Snapdragon 801";
            case "MSM8274AC v3":
                procName = "Snapdragon 801";
            case "MSM8974AC v3":
                procName = "Snapdragon 801";
            case "APQ8084":
                procName = "Snapdragon 805";
            case "MSM8992":
                procName = "Snapdragon 808";
            case "MSM8994":
                procName = "Snapdragon 810";
            case "MSM8996":
                procName = "Snapdragon 820";
            case "MSM8996 Pro-AB":
                procName = "Snapdragon 821";
            case "MSM8996 Pro-AC":
                procName = "Snapdragon 821";
            case "MSM8998":
                procName = "Snapdragon 835";
            case "SDM845":
                procName = "Snapdragon 845";
            case "SM8150":
                procName = "Snapdragon 845";
            case "SM8150-AC":
                procName = "Snapdragon 855+";
            case "SM8250":
                procName = "Snapdragon 865";
            default:
                procName = Build.MODEL;
                break;
        }
        return procName;
    }

    public String GetGovernor() {
        StringBuffer sb = new StringBuffer();

        //String file = "/proc/cpuinfo";  // Gets most cpu info (but not the governor)
        String file = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";  // Gets governor

        //java.io.RandomAccessFile reader = null;
        //try {
        //    reader = new java.io.RandomAccessFile("/sys/bus/cpu/devices/cpu0/cpufreq/cpuinfo_cur_freq", "r");
        //} catch (IOException ex) {
        //    ex.printStackTrace();
        //}

        if (new File(file).exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(new File(file)));
                String aLine;
                while ((aLine = br.readLine()) != null)
                    sb.append(aLine).append("\n");

                if (br != null)
                    br.close();

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (sb.length() < 1)
        {
            sb.append("unkown");
        }

        return sb.toString();
    }
}
