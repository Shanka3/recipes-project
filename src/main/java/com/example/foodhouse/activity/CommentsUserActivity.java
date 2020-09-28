package com.example.foodhouse.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodhouse.R;

public class CommentsUserActivity extends AppCompatActivity {


    private TextView foodDescription, foodName;
    private ImageView foodImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_user);

        getSupportActionBar().hide();

        foodName = (TextView) findViewById(R.id.txtRecipeName);
        foodDescription = (TextView) findViewById(R.id.txtDescription);
        foodImage = (ImageView) findViewById(R.id.ivImage2);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            foodName.setText(bundle.getString("RecipeName"));
            foodDescription.setText(bundle.getString("Description"));

            Glide.with(this)
                    .load(bundle.getString("Image"))
                    .into(foodImage);
        }
    }
}