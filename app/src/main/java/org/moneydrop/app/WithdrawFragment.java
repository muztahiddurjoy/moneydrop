package org.moneydrop.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.moneydrop.app.DataClasses.WithdrawDataset;


public class WithdrawFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public WithdrawFragment() {
        // Required empty public constructor
    }


    public static WithdrawFragment newInstance(String param1, String param2) {
        WithdrawFragment fragment = new WithdrawFragment();
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

    private DatabaseReference reference;
    private DatabaseReference referencetwo;
    TextView balance,rate,method;
    CardView bkash,nogod,rocket;
    TextInputLayout number;
    Button req;
    TextInputEditText amount,wnumber;
    int bal;
    final String[] way = {""};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_withdraw, container, false);
        balance = root.findViewById(R.id.balance_withdraw);
        rate = root.findViewById(R.id.text_rate);
        method = root.findViewById(R.id.payment_method);
        req = root.findViewById(R.id.btn_send_req_withdraw);
        bkash = root.findViewById(R.id.card_bkash);
        rocket = root.findViewById(R.id.card_rocket);
        nogod = root.findViewById(R.id.card_nogod);
        number = root.findViewById(R.id.withdraw_number_lay);
        amount = root.findViewById(R.id.withdraw_amount);
        wnumber = root.findViewById(R.id.withdraw_number);
        reference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        referencetwo = FirebaseDatabase.getInstance().getReference();
        referencetwo.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rate.setText("Current Rate: "+snapshot.child("rate").child("coin").getValue().toString()+" coins ="+snapshot.child("rate").child("money").getValue().toString()+" Taka");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bal = Integer.parseInt(snapshot.child("balance").getValue(String.class));
                balance.setText(String.valueOf(bal));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        bkash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                way[0] = "bkash";
                number.setHint("Bkash Number");
                method.setText("Payment Method: Bkash");
            }
        });
        nogod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                way[0] = "nogod";
                number.setHint("Nogod Number");
                method.setText("Payment Method: Nogod");
            }
        });
        rocket.setOnClickListener((v)->{
            way[0] = "rocket";
            number.setHint("Rocket Number");
            method.setText("Payment Method: Rocket");
        });
        req.setOnClickListener((v)->{
            if (amount.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), "Please give an amount", Toast.LENGTH_SHORT).show();
            }
            else if (wnumber.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), "Please enter your"+way[0]+" number", Toast.LENGTH_SHORT).show();
            }
            else if (way[0].isEmpty()){
                Toast.makeText(getActivity(), "Please Select a method", Toast.LENGTH_SHORT).show();
            }
            else {
                referencetwo.child("withdraw").push().setValue(new WithdrawDataset(amount.getText().toString(), wnumber.getText().toString(), way[0],FirebaseAuth.getInstance().getCurrentUser().getUid())).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child("balance").setValue(String.valueOf(bal - Integer.parseInt(amount.getText().toString()))).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                amount.setText("");
                                wnumber.setText("");
                                way[0] ="";
                                Toast.makeText(getActivity(), "Withdraw Request Sent!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
        return root;
    }
}