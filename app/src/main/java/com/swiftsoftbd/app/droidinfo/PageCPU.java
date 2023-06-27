package com.swiftsoftbd.app.droidinfo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.swiftsoftbd.app.droidinfo.tools.CPUData;
import com.swiftsoftbd.app.droidinfo.tools.DroidValuePair;

import java.util.Objects;

public class PageCPU extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cpu, container, false);
        Context context = Objects.requireNonNull(getActivity()).getApplicationContext();

        ScrollView scrollView = rootView.findViewById(R.id.scrollCPU);

        TableLayout tableLayout = scrollView.findViewById(R.id.table_cpu);

         CPUData cpu = new CPUData();

         cpu.GetProcessorInfo();
        //cpu.getCpuinfo();
        //cpu.loadCpuInfo();

        TextView txtProcessorMfg = (TextView) rootView.findViewById(R.id.txtProcessorMfg);
        txtProcessorMfg.setText(cpu.GetProcessorMfg(context));

        TextView txtProcessorModel = (TextView) rootView.findViewById(R.id.txtProcessorModel);
        txtProcessorModel.setText(cpu.GetProcessorModel(context));

        TextView txtArchitecture = (TextView) rootView.findViewById(R.id.txtArchitecture);
        txtArchitecture.setText(cpu.GetArchitecture());

        TextView txtCPUCores = (TextView) rootView.findViewById(R.id.txtCPUCores);
        txtCPUCores.setText(cpu.GetCPUCores()+" cores");

        TextView txtFrequencies = (TextView) rootView.findViewById(R.id.txtFrequencies);
        txtFrequencies.setText(cpu.getCPUFreqMHz());

        TextView txtGovernor = (TextView) rootView.findViewById(R.id.txtGovernor);
        txtGovernor.setText(cpu.GetGovernor());

        //TextView txtProcessorInfo = (TextView) rootView.findViewById(R.id.txtProcessorInfo);
        //txtProcessorInfo.setText(cpu.GetProcessorInfo());

        int paddingDp = 3;
        float density = context.getResources().getDisplayMetrics().density;
        int paddingPixel = (int)(paddingDp * density);
        //view.setPadding(0,paddingPixel,0,0);

        int counter = 1;
//        for (Map.Entry<String, String> entry : cpu.processorMap.entrySet()) {
        for (DroidValuePair pair : cpu.processorInfo) {

            //for (Map.Entry<String, String> entry : cpu.processorMap.entrySet())
            //{
            //    System.out.println(entry.getKey() + "/" + entry.getValue());
            //}

            TableRow tableRow = new TableRow(context);

            if (counter % 2 == 0) {
                tableRow.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            else
            {
                tableRow.setBackgroundColor(Color.parseColor("#FAFAFA"));
            }

            //ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams();
            //tableRow.MarginLayoutParams(new ViewGroup.MarginLayoutParams())
            //ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) tableRow
            //        .getLayoutParams();

            //mlp.setMargins(0, 0, 0, 1);
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            //params.bottomMargin = 5;
            params.setMargins(0, 0, 0, 5);
            tableRow.setLayoutParams(params);

            counter++;

            TextView leftView = new TextView(context);
            leftView.setLayoutParams(new TableRow.LayoutParams(1));
            leftView.setTextColor(getResources().getColor(android.R.color.black));
            leftView.setGravity(Gravity.START);
            leftView.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
            leftView.setText(pair.getFirstValue());


            TextView rightView = new TextView(context);
           // rightView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            rightView.setLayoutParams(new TableRow.LayoutParams(2));
            rightView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            rightView.setTextColor(getResources().getColor(android.R.color.black));
            rightView.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
            rightView.setGravity(Gravity.END);
            rightView.setText(pair.getSecondValue());

            tableRow.addView(leftView);
            tableRow.addView(rightView);
            tableLayout.addView(tableRow);
        }
        return rootView;
    }
}
