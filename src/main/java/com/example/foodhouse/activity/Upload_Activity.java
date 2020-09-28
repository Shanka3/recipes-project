package com.example.foodhouse.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodhouse.R;
import com.example.foodhouse.model.FoodData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

public class Upload_Activity extends AppCompatActivity {

    private ImageView recipeImage;
    private Uri uri;
    private EditText txt_name, txt_description, txt_method;
    private String imageUrl;
    private String post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_activity);
        getSupportActionBar().setTitle("Upload Your Recipes");

        recipeImage = (ImageView) findViewById(R.id.iv_foodImage);
        txt_name = (EditText) findViewById(R.id.txt_Recipe_Name);
        txt_description = (EditText) findViewById(R.id.txt_Description);
        txt_method = (EditText) findViewById(R.id.txt_method);

    }

    public void btnSelectImage(View view) {

        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        startActivityForResult(photoPicker, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            uri = data.getData();
            recipeImage.setImageURI(uri);

        } else Toast.makeText(this, "You Haven't Picked Any Image", Toast.LENGTH_SHORT).show();
    }


    public void uploadImage() {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("RecipeImage").child(uri.getLastPathSegment());

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Recipe Uploading...");
        progressDialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                uploadRecipe();
                progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
            }
        });
    }

    public void btnUploadRecipe(View view) {
        String upName = txt_name.getText().toString().trim();
        String upDescription = txt_description.getText().toString().trim();
        String upMethod = txt_method.getText().toString().trim();
        Pattern regex = Pattern.compile("[$+:;=\\\\?@#|<>^*%!]");

        if (uri == null) {
            Toast.makeText(this, "Image Not Selected", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(upName)) {
            txt_name.setError("Recipe Name Is Empty");
            return;
        } else if (regex.matcher(upName).find()) {
            txt_name.setError("SpecialCharacters Not Allowed");
            return;
        } else if (TextUtils.isEmpty(upDescription)) {
            txt_description.setError("Description Is Empty");
            return;
        } else if (regex.matcher(upDescription).find()) {
            txt_description.setError("SpecialCharacters Not Allowed");
            return;
        } else if (TextUtils.isEmpty(upMethod)) {
            txt_method.setError("Preparation Is Empty");
            return;
        } else if (regex.matcher(upMethod).find()) {
            txt_method.setError("SpecialCharacters Not Allowed");
            return;
        } else uploadImage();
    }

    public void uploadRecipe() {

        String myCurrentDateTime = DateFormat.getDateTimeInstance()
                .format(Calendar.getInstance().getTime());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("Recipe").child(myCurrentDateTime);

          post = myref.getKey();


        FoodData foodData = new FoodData(
                txt_name.getText().toString(),
                txt_description.getText().toString(),
                txt_method.getText().toString(),
                imageUrl,
                post



        );


                myref.setValue(foodData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(Upload_Activity.this, "Recipe Uploaded", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Upload_Activity.this, "Error !" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}