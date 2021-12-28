package org.moneydrop.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.moneydrop.app.DataClasses.TaskDataset;

import java.util.ArrayList;


public class TaskActivity extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public TaskActivity() {
    }

    public static TaskActivity newInstance(String param1, String param2) {
        TaskActivity fragment = new TaskActivity();
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

    private RecyclerView  recyclerView;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private TaskAdapter adapter;
    private ArrayList<TaskDataset> arrayList;
    private ArrayList<String> keys;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_task_activity, container, false);
        recyclerView = root.findViewById(R.id.recycler_task_fragment);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid()).child("tasks");
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        arrayList = new ArrayList<>();
        keys = new ArrayList<>();
        adapter = new TaskAdapter(arrayList,keys,getActivity(),true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                keys.clear();
              for(DataSnapshot ds : snapshot.getChildren()){
                  TaskDataset data = ds.getValue(TaskDataset.class);
                  if (data.getState().equals("undone")){
                      String key = ds.getKey();
                      arrayList.add(data);
                      keys.add(key);
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