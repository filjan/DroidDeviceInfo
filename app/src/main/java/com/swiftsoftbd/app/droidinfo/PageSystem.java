package com.swiftsoftbd.app.droidinfo;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import com.swiftsoftbd.app.droidinfo.tools.SystemData;

public class PageSystem extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_system, container, false);
        Context context = getActivity().getApplicationContext();

        ScrollView scrollView = rootView.findViewById(R.id.scrollSystem);

        TableLayout tableLayout = scrollView.findViewById(R.id.table_system);

        SystemData system = new SystemData();

        TextView txtAndroidVersion = (TextView) rootView.findViewById(R.id.txtAndroidVersion);
        txtAndroidVersion.setText(system.getAndroidVersion(context));

        TextView txtAPILevel = (TextView) rootView.findViewById(R.id.txtAPILevel);
        txtAPILevel.setText(system.getAPILevel(context));

        TextView txtAndroidID = (TextView) rootView.findViewById(R.id.txtAndroidID);
        txtAndroidID.setText(system.getAndroidID(context));

        TextView txtKernelArchitecture = (TextView) rootView.findViewById(R.id.txtKernelArchitecture);
        txtKernelArchitecture.setText(system.getKernelArchitecture(context));

        TextView txtBuildID = (TextView) rootView.findViewById(R.id.txtBuildID);
        txtBuildID.setText(system.getBuildID(context));

        TextView txtRootAccess = (TextView) rootView.findViewById(R.id.txtRootAccess);
        txtRootAccess.setText(system.getRootAccess(context));

        TextView txtKernel = (TextView) rootView.findViewById(R.id.txtKernel);
        txtKernel.setText(system.getKernel(context));

        TextView txtJavaVM = (TextView) rootView.findViewById(R.id.txtJavaVM);
        txtJavaVM.setText(system.getJavaVM(context));

        TextView txtLanguage = (TextView) rootView.findViewById(R.id.txtLanguage);
        txtLanguage.setText(system.getLanguage(context));

        TextView txtTimeZone = (TextView) rootView.findViewById(R.id.txtTimeZone);
        txtTimeZone.setText(system.getTimeZone());

        TextView txtSinceReboot = (TextView) rootView.findViewById(R.id.txtSinceReboot);
        txtSinceReboot.setText(system.getSinceReboot(context));

        TextView txtDensity = (TextView) rootView.findViewById(R.id.txtDensity);
        txtDensity.setText(system.getDensity(context));

        TextView txtScreenResolution = (TextView) rootView.findViewById(R.id.txtScreenResolution);
        txtScreenResolution.setText(system.getScreenResolution(context));

        TextView txtRefreshRate = (TextView) rootView.findViewById(R.id.txtRefreshRate);
        txtRefreshRate.setText(system.getRefreshRate(context));

        return rootView;
    }
}
