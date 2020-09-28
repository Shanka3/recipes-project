package com.example.foodhouse.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodhouse.R;

public class RatingActivity extends AppCompatActivity {


    TextView txtRating;
    RatingBar ratingBar;
    Button RatingButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        getSupportActionBar().setTitle("Recipe Ratings");

        txtRating = findViewById(R.id.TxtRating);
        ratingBar = findViewById(R.id.ratingBar);
        RatingButton = findViewById(R.id.RatingButton);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                txtRating.setText("Rate: "+rating);

            }
        });

        RatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RatingActivity.this, "Thanks For Your Ratings", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}