package com.swiftsoftbd.app.droidinfo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * A fragment that launches other parts of the demo application.
 */
public class PageAbout extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);


        rootView.findViewById(R.id.btnRate).setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View view) {
        		//Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=com.swiftsoftbd.app.droidinfo");
                Uri uri = Uri.parse("market://details?id=com.swiftsoftbd.app.droidinfo");
            	Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

                final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        // handle the twitter button press
        rootView.findViewById(R.id.btnTwitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // find out if the twitter app is installed or not
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("twitter://user?screen_name=filjaninc"));
                    startActivity(intent);

                }catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://twitter.com/#!/filjaninc")));
                }
            }
        });

        rootView.findViewById(R.id.btnFacebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // find out if the twitter app is installed or not
                try {
                    getContext().getApplicationContext().getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/filjaninc"));
                    startActivity(intent);

                }catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.facebook.com/filjaninc")));
                }
            }
        });

        rootView.findViewById(R.id.btnEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // find out if the twitter app is installed or not
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@filjan.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Droid Device Info Feedback");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext().getApplicationContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rootView.findViewById(R.id.btnPrivacy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.filjan.com/mobile-app-privacy-policy/"));
                    startActivity(browserIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getContext().getApplicationContext(), "There is no web browser installed.", Toast.LENGTH_SHORT).show();
                }            }
        });

        return rootView;
    }
}
