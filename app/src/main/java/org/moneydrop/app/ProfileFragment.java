package org.moneydrop.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.moneydrop.app.DataClasses.TaskDataset;
import org.moneydrop.app.DataClasses.UserDatasetClass;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ProfileFragment() {}

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    private TextView name, email, phone, balance;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceprof;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private ArrayList<String> keys;
    private ArrayList<TaskDataset> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        name = root.findViewById(R.id.name_profile);
        email = root.findViewById(R.id.email_profile);
        phone = root.findViewById(R.id.phone_profile);
        balance = root.findViewById(R.id.balance_profile);
        keys = new ArrayList<String>();
        arrayList = new ArrayList<TaskDataset>();
        recyclerView = root.findViewById(R.id.recycler_done_profile);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2,RecyclerView.VERTICAL,true));
        adapter = new TaskAdapter(arrayList,keys,getActivity(),false);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("tasks");
        databaseReferenceprof = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        databaseReferenceprof.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDatasetClass datasetClass = snapshot.getValue(UserDatasetClass.class);
                name.setText(datasetClass.getName());
                email.setText(datasetClass.getEmail());
                phone.setText(datasetClass.getPhone());
                balance.setText("Balance: "+datasetClass.getBalance());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                keys.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    TaskDataset dataset = ds.getValue(TaskDataset.class);
                    if (!arrayList.contains(dataset)) {
                        if (dataset.getState().equals("done")) {
                            arrayList.add(dataset);
                            keys.add(ds.getKey());
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView.setAdapter(adapter);

        return root;
    }
}