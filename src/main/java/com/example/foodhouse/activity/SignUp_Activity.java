package com.example.foodhouse.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodhouse.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp_Activity extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText name,phone,emailid,passwordid;
    Button signup;
    TextView create;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setTitle("Sign Up");

        name = findViewById(R.id.userName);
        phone = findViewById(R.id.Phone);
        emailid = findViewById(R.id.Email);
        passwordid = findViewById(R.id.Password);
        signup = findViewById(R.id.signupRegister);
        create = findViewById(R.id.account);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(),RecipeActivity.class));
        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Mail = emailid.getText().toString().trim();
                String Pass = passwordid.getText().toString().trim();
                final String uname = name.getText().toString();
                final String number = phone.getText().toString();

                if (TextUtils.isEmpty(uname)){
                    name.setError("Name Is Required");
                    return;
                }

                if (TextUtils.isEmpty(number)){
                    phone.setError("Phone Number Is Required");
                    return;
                }


                if (TextUtils.isEmpty(Mail)){
                    emailid.setError("Email Is Required");
                    return;
                }

                if (TextUtils.isEmpty(Pass)){
                    passwordid.setError("Password Is Required");
                    return;
                }

                if (Pass.length() < 6) {
                    passwordid.setError("Password Must Be >= 6 Character");
                    return;
                }

                progressDialog.setMessage("Creating Account...");
                progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(Mail,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(SignUp_Activity.this, "Account Created", Toast.LENGTH_SHORT).show();
                            userID = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Name",uname);
                            user.put("Email",Mail);
                            user.put("Phone",number);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user profile is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            finish();
                            startActivity(new Intent(getApplicationContext(),RecipeActivity.class));
                        }else {
                            Toast.makeText(SignUp_Activity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();

                        }
                    }
                });


            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), SignIn_Activity.class));
            }
        });

    }
}