package org.moneydrop.app.Services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.moneydrop.app.SplashScreen;

import java.util.Map;
import java.util.Random;

public class FirebaseMessagingServiceMoneyDropApp extends FirebaseMessagingService {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        reference.child("token").setValue(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        sendNotification(remoteMessage);
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        Map<String,String> data  = remoteMessage.getData();
        String title = data.get("title");
        String content = data.get("content");
        Intent notifyIntent = new Intent(this, SplashScreen.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "Money Drop";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            @SuppressLint("WrongConstant")
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,"Money Drop",
                    NotificationManager.IMPORTANCE_MAX);
            channel.setDescription("Notification from Money Drop");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.setVibrationPattern(new long[]{0,500,0,500});
            channel.enableVibration(true);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setTicker("Money Drop")
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(notifyPendingIntent)
                .setContentInfo("Money Drop");
        manager.notify(new Random().nextInt(1000),builder.build());
    }
}
