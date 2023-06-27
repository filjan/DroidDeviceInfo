/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.swiftsoftbd.app.droidinfo;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.swiftsoftbd.app.droidinfo.adapter.PagerAdapter;
import com.swiftsoftbd.app.droidinfo.tools.LoaderData;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public class ActivityMain extends FragmentActivity implements ActionBar.TabListener {

    
    PagerAdapter mAppSectionsPagerAdapter;

    private String[] tabTitle;
    //for ads
    ViewPager mViewPager;
    ActionBar actionBar;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private View view;
    Context context;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);

        AppCenter.start(getApplication(), "de879b16-07e9-4032-8789-a8c6231e4032",
                Analytics.class, Crashes.class);

        // Create the InterstitialAd and set the adUnitId.
        //mInterstitialAd = new InterstitialAd(this);
        // Defined in res/values/strings.xml
        //mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        //prepare ads
        //AdRequest adRequest = new AdRequest.Builder().build();
        //mInterstitialAd.loadAd(adRequest);

        //makeActionOverflowMenuShown();
        tabTitle = getResources().getStringArray(R.array.tab_title);
        
        mAppSectionsPagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabTitle.length);

        // Set up the action bar.
        actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //AdView mAdView = (AdView) rootView.findViewById(R.id.ad_view_all);
        //AdView adView = (AdView) findViewById(R.id.ad_view_all);
        //AdRequest adRequestBanner = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        // Start loading the ad in the background.
        try
        {
            //adView.loadAd(adRequestBanner);

        } catch ( Exception ex ) {
            ex.printStackTrace();
        }

        mViewPager = (ViewPager) findViewById(R.id.pager);
        defineTabAnpageView();

        context = this.getApplicationContext();

    }
    
    public void defineTabAnpageView(){
    	mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            //if (i != 3) { // skip over camera
                actionBar.addTab(actionBar.newTab().setText(tabTitle[i]).setTabListener(this));
            //}
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    	//mAppSectionsPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
    	
    	//showInterstitial();
    	
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    	//mAppSectionsPagerAdapter.notifyDataSetChanged();
    }

    

    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	this.menu=menu;
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
    private Menu menu;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.refresh) {
            new LoaderInfo(this).execute("");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
    
    
    public class LoaderInfo extends AsyncTask<String, String, String>{
    	LoaderData cpu = null;
    	String status = "failed";
    	Context context;
    	private LoaderInfo(Activity act){
    		context=act;
    		cpu = new LoaderData(act);
    		menu.getItem(0).setVisible(false);
    		setProgressBarIndeterminateVisibility(true);
    	}

		@Override
		protected String doInBackground(String... params) {
			try {
				cpu.loadCpuInfo();
		     	cpu.loadBateryInfo();
		     	cpu.loadDeviceInfo();
		     	cpu.loadSystemInfo();
		     	cpu.loadSupportInfo();
		     	cpu.loadWifiInfo();
		     	status="succced";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		
				
		@Override
		protected void onPostExecute(String result) {
			setProgressBarIndeterminateVisibility(false);
			menu.getItem(0).setVisible(true);
			if(!status.equals("failed")){
				Toast.makeText(context, "Info updated", Toast.LENGTH_SHORT).show();
				//refresh view
				mAppSectionsPagerAdapter.notifyDataSetChanged();
			}
			super.onPostExecute(result);
		}

    }
    
    /**
	  * show ads
	  */
	public void showInterstitial() {
		// Show the ad if it's ready
		//if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
		//	mInterstitialAd.show();
		//}
	}


}
