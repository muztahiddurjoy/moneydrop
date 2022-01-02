package org.moneydrop.app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private DatabaseReference reference;
    private int point;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ads, container, false);
        reference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("balance");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    point = Integer.parseInt(String.valueOf(snapshot.getValue()));
                }
                else {
                    Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                            Log.d("",ad.errorMessage);
                        }

                        @Override
                        public void adClicked(Ad ad) {
                            Toast.makeText(getActivity(), "Ad Clicked", Toast.LENGTH_SHORT).show();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    reference.setValue(++point).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(getActivity(), "1 point has been added!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }).start();

                        }

                        @Override
                        public void adNotDisplayed(Ad ad) {

                        }
                    });
                }

                @Override
                public void onFailedToReceiveAd(@Nullable Ad ad) {
                    Toast.makeText(getActivity(), "Please Click Again!", Toast.LENGTH_SHORT).show();
                }
            });
        });
        return root;
    }
}