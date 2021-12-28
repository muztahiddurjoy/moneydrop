package org.moneydrop.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.adlisteners.AdDisplayListener;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.startapp.sdk.adsbase.adlisteners.VideoListener;


public class AdsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AdsFragment() {

    }


    public static AdsFragment newInstance(String param1, String param2) {
        AdsFragment fragment = new AdsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private CardView showbtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ads, container, false);
        showbtn = root.findViewById(R.id.showads);
        showbtn.setOnClickListener((v)->{
            final StartAppAd appAd = new StartAppAd(getActivity());
            appAd.setVideoListener(new VideoListener() {
                @Override
                public void onVideoCompleted() {
                    Toast.makeText(getActivity(), "Video Finished", Toast.LENGTH_SHORT).show();
                }
            });

            appAd.loadAd(new AdEventListener() {
                @Override
                public void onReceiveAd(@NonNull Ad ad) {

                    appAd.showAd(new AdDisplayListener() {
                        @Override
                        public void adHidden(Ad ad) {
                            Toast.makeText(getActivity(), "Ad Hidden", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void adDisplayed(Ad ad) {
                            Toast.makeText(getActivity(), "Ad Displayed", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void adClicked(Ad ad) {
                            Toast.makeText(getActivity(), "Ad Clicked", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void adNotDisplayed(Ad ad) {

                        }
                    });
                }

                @Override
                public void onFailedToReceiveAd(@Nullable Ad ad) {
                    Toast.makeText(getActivity(), ad.errorMessage.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        });
        return root;
    }
}