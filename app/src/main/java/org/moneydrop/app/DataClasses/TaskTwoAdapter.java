package org.moneydrop.app.DataClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.moneydrop.app.R;

import java.util.ArrayList;

public class TaskTwoAdapter extends RecyclerView.Adapter<TaskTwoAdapter.TaskTwoViewHolder> {
    ArrayList<String> keys;
    ArrayList<TaskTwoDataset> arrayList;
    Context context;
    Boolean clickable;

    public TaskTwoAdapter(ArrayList<String> keys, ArrayList<TaskTwoDataset> arrayList, Context context, Boolean clickable) {
        this.keys = keys;
        this.arrayList = arrayList;
        this.context = context;
        this.clickable = clickable;
    }

    @NonNull
    @Override
    public TaskTwoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskTwoViewHolder(LayoutInflater.from(context).inflate(R.layout.task_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskTwoViewHolder holder, int position) {
        TaskTwoDataset dataset = arrayList.get(position);
        String key = keys.get(position);
        if (dataset.getState().equals("done")){
            this.clickable = false;
        }
        holder.textView.setText(dataset.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                if (clickable){
                    holder.textView.setTextColor(Color.GRAY);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(dataset.getLink()));
                    context.startActivity(intent);
                    clickable = false;
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
