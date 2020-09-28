package com.example.foodhouse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodhouse.activity.RecipeActivity;
import com.example.foodhouse.activity.RecipesActivity;
import com.example.foodhouse.activity.SignIn_Activity;
import com.example.foodhouse.activity.SignUp_Activity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button login , register;
    TextView skip;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        login = findViewById(R.id.btnlogin);
        register = findViewById(R.id.btnregister);
        skip = findViewById(R.id.skiper);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), RecipeActivity.class));
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Login has been clicked", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(), SignIn_Activity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Register has been clicked", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(), SignUp_Activity.class));
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RecipesActivity.class));
            }
        });


    }
}