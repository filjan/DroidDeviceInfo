package com.swiftsoftbd.app.droidinfo.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.swiftsoftbd.app.droidinfo.PageAbout;
import com.swiftsoftbd.app.droidinfo.PageAllFragment;
import com.swiftsoftbd.app.droidinfo.PageCPU;
import com.swiftsoftbd.app.droidinfo.PageWifi;
import com.swiftsoftbd.app.droidinfo.PageDevice;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
 * sections of the app.
 */
public class PagerAdapter extends FragmentPagerAdapter {

	private int count;
    public PagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count=count;
    }
    
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int i) {
    	if(i==(count - 1)) {
            // The first section of the app is the most interesting -- it offers
            // a launchpad into the other demonstrations in this swiftsoftbd application.
            return new PageAbout();
        }
        else if (i == 0) {
    	    return new PageCPU();
        }
        else if (i == 1) {
    	    return new PageDevice();
        }
        else if (i == 4) {
    	    return new PageWifi();
    	}else{
    		 // The other sections of the app are dummy placeholders.
            Fragment fragment = new PageAllFragment();
            Bundle args = new Bundle();
            args.putInt(PageAllFragment.ARG_SECTION_NUMBER, i + 1);
            fragment.setArguments(args);
            return fragment;
    	}
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Section " + (position + 1);
    }
}
