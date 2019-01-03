package com.swiftsoftbd.app.droidinfo.tools;
import android.os.Build;
import java.lang.reflect.Field;
import java.text.DateFormat;

public class OSData {

    public String GetOSVersionName()
    {
        return "";
    }

    public String GetOSAPI()
    {
        return  Build.VERSION.SDK_INT + "";
    }

    public String GetOSBuild()
    {
        return "";
    }

    public String GetOSBuildID()
    {
        return Build.ID;
    }

    public String GetOSBuildTime()
    {
        return DateFormat.getDateInstance().format(Build.TIME).toString();
    }

    public static String GetOSVersion()
    {
        Field[] fields = Build.VERSION_CODES.class.getFields();
        String osName = fields[Build.VERSION.SDK_INT + 1].getName();

        return osName;
    }
    public static String currentVersion(){
        String os = Build.VERSION.SDK;

        if (os.equals("28"))
        {
            return "Pie";
        }
        else if (os.equals("27"))
        {
            return "Oreo MR1";
        }
        else if (os.equals("26"))
        {
            return "Oreo";
        }
        if (os.equals("25"))
        {
            return "Nougat++";
        }
        else if (os.equals("24")) {
            return "Nougat";
        }
        else if (os.equals("23")) {
            return "Marshmallow";
        } else if (os.equals("21")) {
            return "Lollipop";
        } else if (os.equals("22")) {
            return "LOLLIPOP_MR1";
        } else if (os.equals("20")) {
            return "KitKat";
        } else if (os.equals("19")) {
            return "KitKat";
        } else if (os.equals("18")) {
            return "Jelly Bean";
        } else if (os.equals("17")) {
            return "Jelly Bean";
        } else if (os.equals("16")) {
            return "Jelly Bean";
        } else if (os.equals("15")) {
            return "Ice Cream Sandwich";
        } else if (os.equals("14")) {
            return "Ice Cream Sandwich";
        } else if (os.equals("13")) {
            return "Honeycomb";
        } else if (os.equals("12")) {
            return "Honeycomb";
        } else if (os.equals("11")) {
            return "Honeycomb";
        } else if (os.equals("10")) {
            return "Gingerbread";
        } else if (os.equals("9")) {
            return "Froyo";
        } else if (os.equals("8")) {
            return "Froyo";
        } else {
            return "Not Found";
        }    }
}
