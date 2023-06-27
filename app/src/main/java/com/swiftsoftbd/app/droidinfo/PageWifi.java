package com.swiftsoftbd.app.droidinfo;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.swiftsoftbd.app.droidinfo.tools.WifiData;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.os.Build.VERSION_CODES.M;

public class PageWifi extends Fragment {
    View rootView;
    TextView txtSentSinceReboot;
    TextView txtReceivedSinceReboot;
    TextView txtSignalStrength;
    TextView txtSpeed;
    Context context;
    private static final int PERMISSION_LOCATION_FINE_CODE = 200;
    private View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_wifi, container, false);
        context = getActivity().getApplicationContext();


        TextView txtWifiEnabled = (TextView) rootView.findViewById(R.id.txtWifiEnabled);
        txtWifiEnabled.setText(WifiData.IsEnabled(context));

/*        TextView txtBSSID = (TextView) rootView.findViewById(R.id.txtBSSID);
        Button lnkBSSID = (Button) rootView.findViewById(R.id.lnkBSSID);
        lnkBSSID.setVisibility(View.VISIBLE);
        lnkBSSID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetBSSID();
            }
        }); */

        if (Build.VERSION.SDK_INT >= M) {
            //ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
            if (ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //txtBSSID.setVisibility(View.GONE);
                //lnkBSSID.setVisibility(View.VISIBLE);
            } else {
                //txtBSSID.setText(WifiData.GetBSSID(context));
            }
        }


        TextView txtChannel = (TextView) rootView.findViewById(R.id.txtChannel);
        txtChannel.setText(WifiData.GetWifiChannel(context));

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

        txtSpeed = (TextView) rootView.findViewById(R.id.txtSpeed);
        txtSpeed.setText(WifiData.GetWifiSpeed(context));

        txtSignalStrength = (TextView) rootView.findViewById(R.id.txtSignalStrength);
        txtSignalStrength.setText(WifiData.GetSignalStrength(context));

        txtReceivedSinceReboot = (TextView) rootView.findViewById(R.id.txtReceivedSinceReboot);
        txtReceivedSinceReboot.setText(WifiData.GetReceivedSinceReboot());

        txtSentSinceReboot = (TextView) rootView.findViewById(R.id.txtSentSinceReboot);
        txtSentSinceReboot.setText(WifiData.GetSentSinceReboot());

        mHandler.postDelayed(mRunnable, 1000);
        return rootView;
    }

    @AfterPermissionGranted(PERMISSION_LOCATION_FINE_CODE)
    public void SetBSSID()
    {
        String[] perms = {ACCESS_FINE_LOCATION};

        if (EasyPermissions.hasPermissions(context, perms))
        {
            Toast.makeText(context, "Already has fine location", Toast.LENGTH_SHORT).show();
            //txtBSSID.setText(WifiData.GetBSSID(context));

        } else {
            //txtBSSID.setVisibility(View.GONE);
            //lnkBSSID.setVisibility(View.VISIBLE);
            Toast.makeText(context, "Requesting permissions", Toast.LENGTH_SHORT).show();
            EasyPermissions.requestPermissions(this, "We need permissions to return the BSSID.",
                    PERMISSION_LOCATION_FINE_CODE, perms);
        }

    }
    private android.os.Handler mHandler = new Handler();

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            txtSpeed.setText(WifiData.GetWifiSpeed(context));
            txtSignalStrength = (TextView) rootView.findViewById(R.id.txtSignalStrength);
            txtReceivedSinceReboot.setText(WifiData.GetReceivedSinceReboot());
            txtSentSinceReboot.setText(WifiData.GetSentSinceReboot());
            mHandler.postDelayed(mRunnable, 1000);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}