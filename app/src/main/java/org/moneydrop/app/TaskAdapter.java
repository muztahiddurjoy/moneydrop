package org.moneydrop.app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.adlisteners.AdDisplayListener;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;

import org.moneydrop.app.DataClasses.TaskDataset;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolderTask> {

    ArrayList<TaskDataset> arrayList;
    ArrayList<String > keys;
    Context context;
    Boolean clickable;

    public TaskAdapter(ArrayList<TaskDataset> arrayList, ArrayList<String> keys, Context context, Boolean clickable) {
        this.arrayList = arrayList;
        this.keys = keys;
        this.context = context;
        this.clickable = clickable;
    }

    @NonNull
    @Override
    public ViewHolderTask onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderTask(LayoutInflater.from(context).inflate(R.layout.task_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTask holder, int position) {
        String val = arrayList.get(position).getName();
        String key = keys.get(position);
        holder.textView.setText(val);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickable){
                    Intent intent = new Intent(context,Task2aActivity.class);
                    intent.putExtra("dbkey",key);
                    intent.putExtra("not",val);
                    context.startActivity(intent);
                }
               // StartAppAd.showAd(context);
                final StartAppAd offer = new StartAppAd(context);
                offer.showAd(new AdDisplayListener() {
                    @Override
                    public void adHidden(Ad ad) {

                    }

                    @Override
                    public void adDisplayed(Ad ad) {

                    }

                    @Override
                    public void adClicked(Ad ad) {
                        offer.close();
                    }

                    @Override
                    public void adNotDisplayed(Ad ad) {

                    }
                });
                offer.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                    @Override
                    public void onReceiveAd(@NonNull Ad ad) {
                        offer.showAd();
                    }

                    @Override
                    public void onFailedToReceiveAd(@Nullable Ad ad) {
                        Toast.makeText(context, ad.errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });

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
