package com.swiftsoftbd.app.droidinfo.tools;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.TrafficStats;
import android.net.wifi.ScanResult;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Enumeration;
import java.util.Arrays;

public class WifiData {
    //private static Context context;

    public static String IsEnabled(Context context)
    {
        String isEnabled = "";

        WifiManager wifi =(WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (wifi.isWifiEnabled()){
            //wifi is enabled
            isEnabled = "Yes";
        }
        else
        {
            isEnabled = "No";
        }

        return isEnabled;
    }

    public  static  String GetWifiSpeed(Context context)
    {
        String linkSpeed = "";

        final WifiManager wifiManager =(WifiManager)context.getSystemService(Context.WIFI_SERVICE);

        if (wifiManager != null) {
            final WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                linkSpeed = String.valueOf(wifiInfo.getLinkSpeed()) + " " + WifiInfo.LINK_SPEED_UNITS;
            }
        }
        return linkSpeed;
    }

    public  static String GetSSID(Context context)
    {
        String ssid = "";

        final WifiManager wifiManager =(WifiManager)context.getSystemService(Context.WIFI_SERVICE);

        if (wifiManager != null) {
            final WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                ssid = wifiInfo.getSSID();
            }
        }
        return ssid;
    }

    public  static  String GetBSSID(Context context)
    {
        String bssid = "";

        final WifiManager wifiManager =(WifiManager)context.getSystemService(Context.WIFI_SERVICE);

        if (wifiManager != null) {
            final WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                bssid = wifiInfo.getBSSID();
            }
        }

        return bssid;
    }

    public  static  String GetIPAddress(Context context)
    {
        String ipAddress = "";

        final WifiManager wifiManager =(WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (wifiManager != null) {
            final WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                int ip = wifiInfo.getIpAddress();

                ipAddress = String.format("%d.%d.%d.%d", (ip & 0xff), (ip >> 8 & 0xff), (ip >> 16 & 0xff),
                        (ip >> 24 & 0xff));
            }
        }

        return ipAddress;
    }

    public static String GetIPv6(Context context)
    {
        final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        final ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt(wifiInfo.getIpAddress());
        try {
            final InetAddress inetAddress = InetAddress.getByAddress(null, byteBuffer.array());
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            //TODO: Return null?
            return "";
        }

        //final Inet6Address inet6Address = getWifiInetAddress(context.getApplicationContext(), Inet6Address.class);
        //return inet6Address.getHostAddress();
    }
    public  static  String GetMacAddress(Context context)
    {
        String macAddress = "";

                macAddress = getMacAddr();

        return macAddress;
    }

    public  static  String GetSignalStrength(Context context)
    {
        return String.valueOf(getWifiSignalStrength(context));
    }

    public static int getWifiSignalStrength(Context context) {
        int returnValue = 0;
        int MIN_RSSI = -100;
        int MAX_RSSI = -55;
        int levels = 101;
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (wifiManager != null) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                int rssi = wifiInfo.getRssi();

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    returnValue = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), levels);
                } else {
                    // this is the code since 4.0.1
                    if (rssi <= MIN_RSSI) {
                        returnValue = 0;
                    } else if (rssi >= MAX_RSSI) {
                        returnValue = levels - 1;
                    } else {
                        float inputRange = (MAX_RSSI - MIN_RSSI);
                        float outputRange = (levels - 1);
                        return (int) ((float) (rssi - MIN_RSSI) * outputRange / inputRange);
                    }
                }
            }
        }

        return  returnValue;
    }

    public  static  String GetSubnet(Context context)
    {
        String subnet = "";

        final WifiManager wifiManager =(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {

            DhcpInfo d=wifiManager.getDhcpInfo();

            /* String s_dns1="DNS 1: "+String.valueOf(d.dns1);
            String s_dns2="DNS 2: "+String.valueOf(d.dns2);
            String s_gateway="Default Gateway: "+String.valueOf(d.gateway);
            String s_ipAddress="IP Address: "+String.valueOf(d.ipAddress);
            String s_leaseDuration="Lease Time: "+String.valueOf(d.leaseDuration);
            String s_netmask="Subnet Mask: "+String.valueOf(d.netmask);
            String s_serverAddress="Server IP: "+String.valueOf(d.serverAddress); */

            subnet = String.valueOf(d.netmask);
        }

        return subnet;
    }

    public static String getMacAddr() {
        String returnValue = "";

        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                Enumeration ee = nif.getInetAddresses();
                while (ee.hasMoreElements()){
                    InetAddress inetAddress = (InetAddress) ee.nextElement();

                    if (!(inetAddress instanceof Inet4Address) && !inetAddress.isLoopbackAddress()) {
                        returnValue = inetAddress.toString();
                    }
                    //System.out.println(i.getHostAddress());
                }
                return returnValue;
            }
        } catch (Exception ex) {
            //handle exception
        }
        return "";
    }

    public static String getWifiMacAddress(Context context) {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

    public static Enumeration<InetAddress> getWifiInetAddresses(final Context context) {
        final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        final WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        final String macAddress = wifiInfo.getMacAddress();
        final String[] macParts = macAddress.split(":");
        final byte[] macBytes = new byte[macParts.length];
        for (int i = 0; i< macParts.length; i++) {
            macBytes[i] = (byte)Integer.parseInt(macParts[i], 16);
        }
        try {
            final Enumeration<NetworkInterface> e =  NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                final NetworkInterface networkInterface = e.nextElement();
                if (Arrays.equals(networkInterface.getHardwareAddress(), macBytes)) {
                    return networkInterface.getInetAddresses();
                }
            }
        } catch (SocketException e) {
            //Log.wtf("WIFIIP", "Unable to NetworkInterface.getNetworkInterfaces()");
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static<T extends InetAddress> T getWifiInetAddress(final Context context, final Class<T> inetClass) {
        final Enumeration<InetAddress> e = getWifiInetAddresses(context);
        while (e.hasMoreElements()) {
            final InetAddress inetAddress = e.nextElement();
            if (inetAddress.getClass() == inetClass) {
                return (T)inetAddress;
            }
        }
        return null;
    }

    public static String GetReceivedSinceReboot()
    {
        String returnValue = "";
        long mStartRX = 0;
        long mStartTX = 0;

        mStartRX = TrafficStats.getTotalRxBytes();
        mStartTX = TrafficStats.getTotalTxBytes();

        if (mStartRX == TrafficStats.UNSUPPORTED || mStartTX == TrafficStats.UNSUPPORTED) {
            // device does not support traffic stat monitoring.");
            returnValue = "";
        } else {
            returnValue = Long.toString(mStartRX) + " bytes";
        }

        return returnValue;
    }

    public static String GetSentSinceReboot()
    {
        String returnValue = "";
        long mStartTX = 0;

        mStartTX = TrafficStats.getTotalTxBytes();

        if (mStartTX == TrafficStats.UNSUPPORTED) {
            // device does not support traffic stat monitoring.");
            returnValue = "";
        } else {
            returnValue = Long.toString(mStartTX) + " bytes";
        }

        return returnValue;
    }

    public static String GetFrequency(final Context context)
    {
        final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        int frequency = 0;

        //wifiManager.startScan();

        Collection<ScanResult> scanResults = wifiManager.getScanResults();
        if (scanResults == null) {
            return "";
        }

        for (ScanResult scanResult : scanResults) {
            frequency = scanResult.frequency;
        }

        return Integer.toString(frequency);
    }
}
