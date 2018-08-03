package com.swiftsoftbd.app.droidinfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.swiftsoftbd.app.droidinfo.adapter.ItemListBaseAdapter;
import com.swiftsoftbd.app.droidinfo.data.SharedPref;
import com.swiftsoftbd.app.droidinfo.tools.LoaderData;

import java.io.IOException;

/**
 * A dummy fragment representing a section of the app, but that simply displays dummy text.
 */
public class PageAllFragment extends Fragment {

    public static final String ARG_SECTION_NUMBER = "section_number";
    ListView listView1;
    TextView text;
    LoaderData cpu;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_page_all, container, false);

		Bundle args = getArguments();
		listView1 = (ListView) rootView.findViewById(R.id.listView1);
		text = (TextView) rootView.findViewById(R.id.text1);

		setView(args.getInt(ARG_SECTION_NUMBER));

		//AdView mAdView = (AdView) rootView.findViewById(R.id.ad_view_all);
		//AdRequest adRequestBanner = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
		// Start loading the ad in the background.
	/*	try
		{
			mAdView.loadAd(adRequestBanner);

	} catch ( Exception ex ) {
		ex.printStackTrace();
	}
*/

        return rootView;
    }
    
    public void setView(int i){
    	String s="";
    	switch (i) {
    	
		case 1: //cpu
			//text.setText(SharedPref.getCPUData(getActivity()));
			s=SharedPref.getCPUData(getActivity());
			listView1.setAdapter(new ItemListBaseAdapter(getActivity(), R.layout.item_listview, LoaderData.getArrList(s)));
			break;
			
		case 2: //device
			//text.setText(SharedPref.getDeviceData(getActivity()));
			s=SharedPref.getDeviceData(getActivity());
			listView1.setAdapter(new ItemListBaseAdapter(getActivity(), R.layout.item_listview, LoaderData.getArrList(s)));
			break;
			
		case 3: //system
			//text.setText(SharedPref.getSystemData(getActivity()));
			s =SharedPref.getSystemData(getActivity());
			listView1.setAdapter(new ItemListBaseAdapter(getActivity(), R.layout.item_listview,LoaderData.getArrList(s)));
			break;
		case 9: //camera
			//text.setText(SharedPref.getSystemData(getActivity()));
			s =SharedPref.getCameraData(getActivity());
//			listView1.setAdapter(new ItemListBaseAdapter(getActivity(), R.layout.item_listview,LoaderData.getArrList(s)));
			break;
			
		case 4: //battery
			//text.setText(SharedPref.getBateryData(getActivity()));
			s =SharedPref.getBateryData(getActivity());
			listView1.setAdapter(new ItemListBaseAdapter(getActivity(), R.layout.item_listview,LoaderData.getArrList(s)));
			break;
			
		case 5: //Wifi
			//text.setText(SharedPref.getSensorData(getActivity()));
//			s =SharedPref.getWifiData(getActivity());
//			listView1.setAdapter(new ItemListBaseAdapter(getActivity(), R.layout.item_listview,LoaderData.getArrList(s)));
			break;
		case 6: //Sensor
			//text.setText(SharedPref.getSensorData(getActivity()));
			s =SharedPref.getSensorData(getActivity());
			listView1.setAdapter(new ItemListBaseAdapter(getActivity(), R.layout.item_listview,LoaderData.getArrList(s)));
			break;
		default:
			break;
		}
    }
    
   
}
