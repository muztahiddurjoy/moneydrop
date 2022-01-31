package org.moneydrop.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import org.moneydrop.app.DataClasses.TaskTwoAdapter;
import org.moneydrop.app.DataClasses.TaskTwoDataset;
import org.moneydrop.app.databinding.ActivityTask2aBinding;

import java.util.ArrayList;

public class Task2aActivity extends AppCompatActivity {
ActivityTask2aBinding binding;
private DatabaseReference reference;
private RecyclerView recyclerView;
private TaskTwoAdapter adapter;
private ArrayList<TaskTwoDataset> arrayList;
private ArrayList<String> keys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityTask2aBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        Bundle bundle = getIntent().getExtras();
        String corekey = bundle.getString("dbkey");
        String nametask = bundle.getString("not");
        getSupportActionBar().setTitle(nametask);
        final StartAppAd appAd = new StartAppAd(this);
        appAd.loadAd(new AdEventListener() {
            @Override
            public void onReceiveAd(@NonNull Ad ad) {

                appAd.showAd(new AdDisplayListener() {
                    @Override
                    public void adHidden(Ad ad) {

                    }

                    @Override
                    public void adDisplayed(Ad ad) {

                    }

                    @Override
                    public void adClicked(Ad ad) {
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

        recyclerView = binding.recyclerTaskTwo;
        arrayList = new ArrayList<TaskTwoDataset>();
        keys = new ArrayList<>();
        adapter = new TaskTwoAdapter(keys,arrayList,this,true, corekey);
        reference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                .child("tasks")
                .child(bundle.getString("dbkey"))
                .child("tasks");
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                keys.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    TaskTwoDataset  dataset = ds.getValue(TaskTwoDataset.class);
                    if (!arrayList.contains(dataset)) {
                            arrayList.add(dataset);
                            keys.add(ds.getKey());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Task2aActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void goBack(View view) {
        startActivity(new Intent(Task2aActivity.this, MainActivity.class));
    }
    public void deleteOper(){
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
    }

    public void markasDone(View view) {

    }
}