package com.swiftsoftbd.app.droidinfo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.swiftsoftbd.app.droidinfo.tools.SystemData;

public class PageSystem extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_system, container, false);
        Context context = getActivity().getApplicationContext();


        return rootView;
    }
}
