package com.swiftsoftbd.app.droidinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.swiftsoftbd.app.droidinfo.tools.BatteryUtils;

public class PageBattery extends Fragment {
    BatteryUtils battery = new BatteryUtils();
    TextView txtBatteryLevel;
    String returnValue;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_battery, container, false);
        Context context = getActivity().getApplicationContext();

        ScrollView scrollView = rootView.findViewById(R.id.scrollBattery);

        TableLayout tableLayout = scrollView.findViewById(R.id.table_battery);

        txtBatteryLevel = (TextView) rootView.findViewById(R.id.txtBatteryLevel);
        batteryLevel(context);
        //txtBatteryLevel.setText(battery.getBatteryLevel(context));

        TextView txtBatteryTechnology = (TextView) rootView.findViewById(R.id.txtBatteryTechnology);
        txtBatteryTechnology.setText(battery.getBatteryTechnology(context));

        TextView txtBatteryPlugged = (TextView) rootView.findViewById(R.id.txtBatteryPlugged);
        txtBatteryPlugged.setText(battery.getPlugTypeString(context));

        TextView txtBatteryHealth = (TextView) rootView.findViewById(R.id.txtBatteryHealth);
        txtBatteryHealth.setText(battery.getHealthString(context));

        TextView txtBatteryStatus = (TextView) rootView.findViewById(R.id.txtBatteryStatus);
        txtBatteryStatus.setText(battery.getStatusString(context));

        TextView txtBatteryVoltage = (TextView) rootView.findViewById(R.id.txtBatteryVoltage);
        txtBatteryVoltage.setText(battery.getBatteryVoltage(context));

        TextView txtBatteryTemperature = (TextView) rootView.findViewById(R.id.txtBatteryTemperature);
        txtBatteryTemperature.setText(battery.getTemperature(context));

        int paddingDp = 3;
        float density = context.getResources().getDisplayMetrics().density;
        int paddingPixel = (int)(paddingDp * density);
        return rootView;
    }

    private String batteryLevel(Context context) {
        BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                //context.unregisterReceiver(this);
                returnValue = battery.getBatteryLevel(context);
            }
        };
        IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        context.registerReceiver(batteryLevelReceiver, batteryLevelFilter);

        return returnValue;
    }
}
