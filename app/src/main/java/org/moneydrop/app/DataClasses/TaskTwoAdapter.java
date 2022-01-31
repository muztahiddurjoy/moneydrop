package org.moneydrop.app.DataClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.moneydrop.app.R;

import java.util.ArrayList;

public class TaskTwoAdapter extends RecyclerView.Adapter<TaskTwoAdapter.TaskTwoViewHolder> {
    ArrayList<String> keys;
    ArrayList<TaskTwoDataset> arrayList;
    Context context;
    Boolean clickable;
    CountDownTimer timer;
    String child;



    public TaskTwoAdapter(ArrayList<String> keys, ArrayList<TaskTwoDataset> arrayList, Context context, Boolean clickable,String child) {
        this.keys = keys;
        this.arrayList = arrayList;
        this.context = context;
        this.clickable = clickable;
        this.child = child;
//        if (arrayList.size()==0){
//            DatabaseReference reference1 = FirebaseDatabase
//                    .getInstance()
//                    .getReference()
//                    .child("users")
//                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                    .child("tasks")
//                    .child(child)
//                    .child("state");
//            reference1.setValue("done");
//            Toast.makeText(context, String.valueOf(arrayList.size()), Toast.LENGTH_SHORT).show();
//        }
    }

    @NonNull
    @Override
    public TaskTwoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskTwoViewHolder(LayoutInflater.from(context).inflate(R.layout.task_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskTwoViewHolder holder, int position) {
//        if (arrayList.size()==0){
//            DatabaseReference reference1 = FirebaseDatabase
//                    .getInstance()
//                    .getReference()
//                    .child("users")
//                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                    .child("tasks")
//                    .child(child)
//                    .child("state");
//            reference1.setValue("done");
//        }
        TaskTwoDataset dataset = arrayList.get(position);
        String key = keys.get(position);

        holder.textView.setText(dataset.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                if (clickable){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(dataset.getLink()));
                    context.startActivity(intent);

                            holder.textView.setTextColor(Color.GRAY);
                            holder.itemView.setClickable(false);
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("tasks").child(child).child("tasks").child(key).child("state");
                            reference.setValue("done").addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            });

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class TaskTwoViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TaskTwoViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_task);
        }
    }
}
