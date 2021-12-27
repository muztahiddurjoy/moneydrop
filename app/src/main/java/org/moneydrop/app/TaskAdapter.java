package org.moneydrop.app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolderTask> {

    ArrayList<String> arrayList;
    ArrayList<String > keys;
    Context context;

    public TaskAdapter(ArrayList<String> arrayList, ArrayList<String> keys, Context context) {
        this.arrayList = arrayList;
        this.keys = keys;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderTask onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderTask(LayoutInflater.from(context).inflate(R.layout.task_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTask holder, int position) {
        String val = arrayList.get(position);
        String key = keys.get(position);
        holder.textView.setText(val);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // StartAppAd.showAd(context);
//                final StartAppAd offer = new StartAppAd(context);
//                offer.setVideoListener(new VideoListener() {
//                    @Override
//                    public void onVideoCompleted() {
//                        Toast.makeText(context, "Granted", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//                offer.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
//                    @Override
//                    public void onReceiveAd(@NonNull Ad ad) {
//                        offer.showAd();
//                    }
//
//                    @Override
//                    public void onFailedToReceiveAd(@Nullable Ad ad) {
//                        Toast.makeText(context, ad.errorMessage, Toast.LENGTH_SHORT).show();
//                    }
//                });
                context.startActivity(new Intent(context,Task2aActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolderTask extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolderTask(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_task);
        }
    }
}
