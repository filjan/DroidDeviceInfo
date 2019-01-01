package com.swiftsoftbd.app.droidinfo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.swiftsoftbd.app.droidinfo.tools.DeviceData;

public class PageDevice extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_device, container, false);
        Context context = getActivity().getApplicationContext();

        DeviceData device = new DeviceData();

        TextView txtModel = (TextView) rootView.findViewById(R.id.txtModel);
        txtModel.setText(device.GetModel());

        TextView txtManufacturer = (TextView) rootView.findViewById(R.id.txtManufacturer);
        txtManufacturer.setText("");

        TextView txtBootloader = (TextView) rootView.findViewById(R.id.txtBootloader);
        txtBootloader.setText("");

        TextView txtSerial = (TextView) rootView.findViewById(R.id.txtSerial);
        txtSerial.setText("");

        TextView txtRadio = (TextView) rootView.findViewById(R.id.txtRadio);
        txtRadio.setText("");

        TextView txtTotalRam = (TextView) rootView.findViewById(R.id.txtTotalRam);
        txtTotalRam.setText("");

        TextView txtAvailableRAM = (TextView) rootView.findViewById(R.id.txtAvailableRAM);
        txtAvailableRAM.setText("");

        TextView txtNetworkType = (TextView) rootView.findViewById(R.id.txtNetworkType);
        txtNetworkType.setText("");

        TextView txtPhoneType = (TextView) rootView.findViewById(R.id.txtPhoneType);
        txtPhoneType.setText("");

        TextView txtTotalStorage = (TextView) rootView.findViewById(R.id.txtTotalStorage);
        txtTotalStorage.setText("");

        TextView txtStorageUsage = (TextView) rootView.findViewById(R.id.txtStorageUsage);
        txtStorageUsage.setText("");

        return rootView;
    }
}