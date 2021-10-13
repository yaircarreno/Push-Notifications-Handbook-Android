package com.yaircarreno.pushnotificationshandbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.yaircarreno.pushnotificationshandbook.databinding.ActivityPushReceiverBinding;

public class PushReceiverActivity extends AppCompatActivity {

    private ActivityPushReceiverBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPushReceiverBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String score = bundle.getString("score");
            String country = bundle.getString("country");
            binding.scoreTextView.setText(getString(R.string.score, score));
            binding.countryTextView.setText(getString(R.string.from, country));
        }
    }
}