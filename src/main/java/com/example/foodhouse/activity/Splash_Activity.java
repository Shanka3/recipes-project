package com.example.foodhouse.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.foodhouse.MainActivity;
import com.example.foodhouse.R;

public class Splash_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);

        getSupportActionBar().hide();

        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep( 2100);

                }
                catch (Exception e) {
                    e.printStackTrace();

                }
                finally {
                    Intent intent = new Intent(Splash_Activity.this , MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            }

        };thread.start();
    }


}