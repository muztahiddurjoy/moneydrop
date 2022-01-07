package org.moneydrop.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class SpinFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public SpinFragment() {

    }

    public static SpinFragment newInstance(String param1, String param2) {
        SpinFragment fragment = new SpinFragment();
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

    private ImageView spinner;
    private Button btn;
    private CountDownTimer timer;
    private DatabaseReference reference;
    private int balance = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View r = inflater.inflate(R.layout.fragment_spin, container, false);
        spinner = r.findViewById(R.id.spinner_wheel);
        btn = r.findViewById(R.id.spin_btn);
        reference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                balance = Integer.parseInt(snapshot.child("balance").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date today = new Date();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("spinlimit", Context.MODE_PRIVATE);
        String limitdate = sharedPreferences.getString("spinlimit","nottoday");
        if (!limitdate.equals(String.valueOf(formatter.format(today)))){
            btn.setEnabled(true);
        }
        else {
            btn.setEnabled(false);
        }

        btn.setOnClickListener((v)->{

            btn.setEnabled(false);
            int spin = new Random().nextInt(20)+10;
            int anspin = spin*=36;
            spinner.setRotation(0);
            int finalSpin = spin;
            timer = new CountDownTimer(anspin *20,1) {
                @Override
                public void onTick(long l) {
                    float rot = spinner.getRotation()+2;
                    spinner.setRotation(rot);
                }

                @Override
                public void onFinish() {
                    int oper =  (finalSpin/36)-10;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    switch (oper){
                        case 0:
                            reference.child("balance").setValue(String.valueOf(balance+800)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    editor.putString("spinlimit", formatter.format(today));
                                    editor.apply();
                                    Toast.makeText(getActivity(), "800 points has been added to your balance!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 1:
                            reference.child("balance").setValue(String.valueOf(balance+70)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    editor.putString("spinlimit", formatter.format(today));
                                    editor.apply();
                                    Toast.makeText(getActivity(), "70 points has been added to your balance!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 2:
                            reference.child("balance").setValue(String.valueOf(balance+200)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    editor.putString("spinlimit", formatter.format(today));
                                    editor.apply();
                                    Toast.makeText(getActivity(), "200 points has been added to your balance!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 3:
                            reference.child("balance").setValue(String.valueOf(balance+150)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    editor.putString("spinlimit", formatter.format(today));
                                    editor.apply();
                                    Toast.makeText(getActivity(), "150 points has been added to your balance!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 4:
                            btn.setEnabled(true);
                            spinner.setRotation(0);
                            Toast.makeText(getActivity(), "Spin Again", Toast.LENGTH_SHORT).show();
                            break;

                        case 5:
                            reference.child("balance").setValue(String.valueOf(balance+70)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    editor.putString("spinlimit", formatter.format(today));
                                    editor.apply();
                                    Toast.makeText(getActivity(), "70 points has been added to your balance!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 6:
                            reference.child("balance").setValue(String.valueOf(balance+300)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    editor.putString("spinlimit", formatter.format(today));
                                    editor.apply();
                                    Toast.makeText(getActivity(), "300 points has been added to your balance!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 7:
                            reference.child("balance").setValue(String.valueOf(balance+150)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    editor.putString("spinlimit", formatter.format(today));
                                    editor.apply();
                                    Toast.makeText(getActivity(), "150 points has been added to your balance!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 8:
                            reference.child("balance").setValue(String.valueOf(balance+50)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    editor.putString("spinlimit", formatter.format(today));
                                    editor.apply();
                                    Toast.makeText(getActivity(), "50 points has been added to your balance!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 9:
                            btn.setEnabled(true);
                            spinner.setRotation(0);
                            Toast.makeText(getActivity(), "Spin Again", Toast.LENGTH_SHORT).show();
                            break;
                            case 10:
                            reference.child("balance").setValue(String.valueOf(balance+300)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    editor.putString("spinlimit", formatter.format(today));
                                    editor.apply();
                                    Toast.makeText(getActivity(), "300 points has been added to your balance!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 11:
                            reference.child("balance").setValue(String.valueOf(balance+0)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    editor.putString("spinlimit", formatter.format(today));
                                    editor.apply();
                                    Toast.makeText(getActivity(), "0 points has been added to your balance!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 12:
                            reference.child("balance").setValue(String.valueOf(balance+50)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    editor.putString("spinlimit", formatter.format(today));
                                    editor.apply();
                                    Toast.makeText(getActivity(), "50 points has been added to your balance!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 13:
                            reference.child("balance").setValue(String.valueOf(balance+700)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    editor.putString("spinlimit", formatter.format(today));
                                    editor.apply();
                                    Toast.makeText(getActivity(), "700 points has been added to your balance!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 14:
                            reference.child("balance").setValue(String.valueOf(balance+100)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    editor.putString("spinlimit", formatter.format(today));
                                    editor.apply();
                                    Toast.makeText(getActivity(), "100 points has been added to your balance!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 15:
                            reference.child("balance").setValue(String.valueOf(balance+0)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    editor.putString("spinlimit", formatter.format(today));
                                    editor.apply();
                                    Toast.makeText(getActivity(), "0 points has been added to your balance!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 16:
                            reference.child("balance").setValue(String.valueOf(balance+10)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    editor.putString("spinlimit", formatter.format(today));
                                    editor.apply();
                                    Toast.makeText(getActivity(), "10 points has been added to your balance!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 17:
                            reference.child("balance").setValue(String.valueOf(balance+700)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    editor.putString("spinlimit", formatter.format(today));
                                    editor.apply();
                                    Toast.makeText(getActivity(), "700 points has been added to your balance!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 18:
                            reference.child("balance").setValue(String.valueOf(balance+100)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    editor.putString("spinlimit", formatter.format(today));
                                    editor.apply();
                                    Toast.makeText(getActivity(), "100 points has been added to your balance!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 19:
                            reference.child("balance").setValue(String.valueOf(balance+200)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    editor.putString("spinlimit", formatter.format(today));
                                    editor.apply();
                                    Toast.makeText(getActivity(), "200 points has been added to your balance!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 20:
                            reference.child("balance").setValue(String.valueOf(balance+10)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    editor.putString("spinlimit", formatter.format(today));
                                    editor.apply();
                                    Toast.makeText(getActivity(), "10 points has been added to your balance!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                    }
                    final StartAppAd appAd = new StartAppAd(getActivity());
                    appAd.setVideoListener(new VideoListener() {
                        @Override
                        public void onVideoCompleted() {
                            //    Toast.makeText(getActivity(), "Video Finished", Toast.LENGTH_SHORT).show();
                        }
                    });

                    appAd.loadAd(new AdEventListener() {
                        @Override
                        public void onReceiveAd(@NonNull Ad ad) {

                            appAd.showAd(new AdDisplayListener() {
                                @Override
                                public void adHidden(Ad ad) {
                                    //    Toast.makeText(getActivity(), "Ad Hidden", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void adDisplayed(Ad ad) {
//                        Toast.makeText(getActivity(), "Ad Displayed", Toast.LENGTH_SHORT).show();
//                        Log.d("",ad.errorMessage);
                                }

                                @Override
                                public void adClicked(Ad ad) {
                                    //  Toast.makeText(getActivity(), "Ad Clicked", Toast.LENGTH_SHORT).show();
//                        reference.setValue(++point).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                             //   Toast.makeText(getActivity(), "1 point has been added!", Toast.LENGTH_SHORT).show();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                            //    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
                                }

                                @Override
                                public void adNotDisplayed(Ad ad) {

                                }
                            });
                        }

                        @Override
                        public void onFailedToReceiveAd(@Nullable Ad ad) {
                            // Toast.makeText(getActivity(), "Please Click Again!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }.start();
        });
        return r;
    }
}