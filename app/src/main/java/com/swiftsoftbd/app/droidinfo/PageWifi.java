package com.swiftsoftbd.app.droidinfo;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.swiftsoftbd.app.droidinfo.tools.WifiData;

public class PageWifi extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wifi, container, false);
        Context context = getActivity().getApplicationContext();

        TextView txtWifiEnabled = (TextView) rootView.findViewById(R.id.txtWifiEnabled);
        txtWifiEnabled.setText(WifiData.IsEnabled(context));

        TextView txtDataState = (TextView) rootView.findViewById(R.id.txtDataState);
        txtDataState.setText("test");

        TextView txtHandshakeState = (TextView) rootView.findViewById(R.id.txtHandshakeState);
        txtHandshakeState.setText("test");

        TextView txtSSID = (TextView) rootView.findViewById(R.id.txtSSID);
        txtSSID.setText(WifiData.GetSSID(context));

        TextView txtBSSID = (TextView) rootView.findViewById(R.id.txtBSSID);
        txtBSSID.setText(WifiData.GetBSSID(context));

        TextView txtVendor = (TextView) rootView.findViewById(R.id.txtVendor);
        txtVendor.setText("test");

        TextView txtChannel = (TextView) rootView.findViewById(R.id.txtChannel);
        txtChannel.setText("test");

        TextView txtFrequency = (TextView) rootView.findViewById(R.id.txtFrequency);
        txtFrequency.setText(WifiData.GetFrequency(context));

        TextView txtIPAddress = (TextView) rootView.findViewById(R.id.txtIPAddress);
        txtIPAddress.setText(WifiData.GetIPAddress(context));

        TextView txtSubnetMask = (TextView) rootView.findViewById(R.id.txtSubnetMask);
        txtSubnetMask.setText(WifiData.GetSubnet(context));

        TextView txtIPv6Address = (TextView) rootView.findViewById(R.id.txtIPv6Address);
        txtIPv6Address.setText(WifiData.GetIPv6(context));

        TextView txtMAC = (TextView) rootView.findViewById(R.id.txtMAC);
        txtMAC.setText(WifiData.GetMacAddress(context));

        TextView txtSpeed = (TextView) rootView.findViewById(R.id.txtSpeed);
        txtSpeed.setText(WifiData.GetWifiSpeed(context));

        TextView txtSignalStrength = (TextView) rootView.findViewById(R.id.txtSignalStrength);
        txtSignalStrength.setText(WifiData.GetSignalStrength(context));

        TextView txtReceivedSinceReboot = (TextView) rootView.findViewById(R.id.txtReceivedSinceReboot);
        txtReceivedSinceReboot.setText(WifiData.GetReceivedSinceReboot());

        TextView txtSentSinceReboot = (TextView) rootView.findViewById(R.id.txtSentSinceReboot);
        txtSentSinceReboot.setText(WifiData.GetSentSinceReboot());

        return rootView;
    }
}