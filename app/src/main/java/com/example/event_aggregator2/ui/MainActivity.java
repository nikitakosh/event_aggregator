package com.example.event_aggregator2.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.event_aggregator2.R;
import com.yandex.mapkit.MapKitFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MapKitFactory.setApiKey("19b80645-9bef-47bf-b2ff-9a008e7c17df");
    }

}