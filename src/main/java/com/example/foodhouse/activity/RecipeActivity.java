package com.example.foodhouse.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodhouse.R;
import com.example.foodhouse.adapter.MyAdapter;
import com.example.foodhouse.model.FoodData;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView mRecyclerView;
    List<FoodData> myFoodList;

    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    ProgressDialog progressDialog;
    MyAdapter myAdapter;
    EditText txt_Search;
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navi_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(),SignIn_Activity.class));
        }

        mRecyclerView =(RecyclerView) findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(RecipeActivity.this,1);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        txt_Search = (EditText)findViewById(R.id.txt_search);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Items...");

        myFoodList = new ArrayList<>();

        final MyAdapter myAdapter = new MyAdapter(RecipeActivity.this, myFoodList);
        mRecyclerView.setAdapter(myAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Recipe");

        progressDialog.show();

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                myFoodList.clear();

                for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()){

                    FoodData foodData = itemSnapshot.getValue(FoodData.class);

                    myFoodList.add(foodData);
                }

                myAdapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });

        txt_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());
            }

            private void filter(String text) {
                ArrayList<FoodData> filterList = new ArrayList<>();

                for (FoodData item: myFoodList){
                    if (item.getItemName().toLowerCase().contains(text.toLowerCase())) {
                        filterList.add(item);
                    }
                }

                myAdapter.filteredList(filterList);
            }
        });

    }

    public void btnUploadActivity(View view) {

        startActivity(new Intent(this,Upload_Activity.class));
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nav_home) {
            startActivity(new Intent(getApplicationContext(), RecipeActivity.class));
        } else if (item.getItemId() == R.id.nav_profile){
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }else if (item.getItemId() == R.id.nav_changeProfile){
            startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
        } else if (item.getItemId() == R.id.nav_Logout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), SignIn_Activity.class));
        }
        return true;

    }
}