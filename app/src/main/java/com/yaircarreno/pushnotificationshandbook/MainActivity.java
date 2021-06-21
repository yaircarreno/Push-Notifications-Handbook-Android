package com.yaircarreno.pushnotificationshandbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ProcessLifecycleOwner;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkPlayServices()) {
            // Great! You can send notifications to this device using FCM.
            getTokenFromFMC();
            debugOperation();
        } else {
            //You won't be able to send notifications to this device
            Log.e(TAG, "Device doesn't have google play services");
        }
    }

    private void getTokenFromFMC() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String token = task.getResult();

                    // Log and toast
                    String msg = "Token created: " + token;
                    Log.d(TAG, msg);
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                });
    }

    private boolean checkPlayServices() {
        final GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.makeGooglePlayServicesAvailable(this);
            } else {
                Toast.makeText(MainActivity.this, R.string.push_error_device_not_compatible, Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        return true;
    }

    private boolean inForeground() {
        return ProcessLifecycleOwner.get().getLifecycle().getCurrentState()
                .isAtLeast(Lifecycle.State.STARTED);
    }

    private void debugOperation() {
        FirebaseDatabase.getInstance()
                .getReference("debug-push-notifications-android")
                .child("onCreate")
                .setValue("Operation called at " + System.currentTimeMillis());
    }
}