package com.yaircarreno.pushnotificationshandbook;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import androidx.annotation.NonNull;

public class PushNotificationHandbookService extends FirebaseMessagingService {

    public static final String TAG = "PushHandbookService";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // You will receive the push notifications here!
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Title: " + remoteMessage.getNotification().getTitle() +
                            "Body: " + remoteMessage.getNotification().getBody());
        }
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Title: " + remoteMessage.getData().get("title") +
                            "Body: " + remoteMessage.getData().get("body"));
        }
        debugOperation();
    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token: " + token);
        // Send the token update on the server here!
    }

    private void debugOperation() {
        FirebaseDatabase.getInstance()
                .getReference("debug-push-notifications-android")
                .child("onMessageReceived")
                .setValue("Operation called at " + System.currentTimeMillis());
    }
}