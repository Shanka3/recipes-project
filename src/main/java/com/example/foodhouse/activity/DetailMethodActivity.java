package com.example.foodhouse.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.foodhouse.R;

public class DetailMethodActivity extends AppCompatActivity {

    private TextView foodMethod,foodName;
    private ImageView foodImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_method);

        getSupportActionBar().hide();

        foodName = (TextView) findViewById(R.id.txtRecipeName);
        foodMethod = (TextView) findViewById(R.id.txtMethod);
        foodImage = (ImageView) findViewById(R.id.ivImage2);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            foodName.setText(bundle.getString("RecipeName"));
            foodMethod.setText(bundle.getString("Method"));

            Glide.with(this)
                    .load(bundle.getString("Image"))
                    .into(foodImage);
        }
    }
}