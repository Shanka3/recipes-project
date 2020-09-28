package com.example.foodhouse.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodhouse.R;
import com.example.foodhouse.newAdapter.NewAdapter;
import com.example.foodhouse.model.FoodData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipesActivity extends AppCompatActivity{

    RecyclerView mRecyclerView;
    List<FoodData> myFoodList;

    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    ProgressDialog progressDialog;
    NewAdapter newAdapter;
    EditText txt_Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Recipes");

        mRecyclerView =(RecyclerView) findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(RecipesActivity.this,1);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        txt_Search = (EditText)findViewById(R.id.txt_search);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Items...");

        myFoodList = new ArrayList<>();

        final NewAdapter newAdapter = new NewAdapter(RecipesActivity.this, myFoodList);
        mRecyclerView.setAdapter(newAdapter);

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

                newAdapter.notifyDataSetChanged();
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

                newAdapter.filteredList(filterList);
            }
        });

    }

}