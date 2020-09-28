package com.example.foodhouse.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodhouse.R;
import com.example.foodhouse.adapter.CommentAdapter;
import com.example.foodhouse.model.Comment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    RecyclerView mCommentRecyclerView;
    RecyclerView twomCommentRecyclerView;
    List<Comment> mComment,twomComment;

     TextView foodName, ratingsUsers;
     ImageView foodImage;
    private EditText userComments;
    private Button userPost;
    FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String PostKey;
    FirebaseDatabase firebaseDatabase;
    CommentAdapter commentAdapter,twocommentAdapter;
    static String COMMENT_KEY = "Comments";
    Button moreless;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().hide();

        foodName = (TextView)findViewById(R.id.txtRecipeName);
        ratingsUsers = (TextView)findViewById(R.id.ratings_btn);
        foodImage = (ImageView)findViewById(R.id.ivImage2);
        userComments = (EditText)findViewById(R.id.comment);
        userPost = (Button)findViewById(R.id.post);
        moreless=(Button)findViewById(R.id.moreless);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        mCommentRecyclerView = (RecyclerView)findViewById(R.id.rv_Comment);
        twomCommentRecyclerView = (RecyclerView)findViewById(R.id.two_rv_Comment);
        userPost.setEnabled(false);

        userComments.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")){
                    userPost.setEnabled(false);
                } else {
                    userPost.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        userPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userPost.setVisibility(View.INVISIBLE);
                DatabaseReference  commentReference = firebaseDatabase.getReference(COMMENT_KEY).child(PostKey).push();
                String comment_content = userComments.getText().toString();
                String publisherID = firebaseUser.getUid();
                Comment comment = new Comment(comment_content,publisherID);

                commentReference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DetailActivity.this, "Comment Added", Toast.LENGTH_SHORT).show();
                        userComments.setText("");
                        userPost.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DetailActivity.this, "Fail To Add Comment", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        Bundle bundle = getIntent().getExtras();

        if (bundle!=null){
            foodName.setText(bundle.getString("RecipeName"));

            Glide.with(this)
                    .load(bundle.getString("Image"))
                    .into(foodImage);
        }

        PostKey = getIntent().getExtras().getString("postKey");

        iniRvComment();

        ratingsUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RatingActivity.class));
            }
        });

        moreless.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(mCommentRecyclerView.getVisibility()==View.VISIBLE)
                {
                    mCommentRecyclerView.setVisibility(View.GONE);
                    twomCommentRecyclerView.setVisibility(View.VISIBLE);
                    moreless.setText("Show more");
                }
                else
                {
                    twomCommentRecyclerView.setVisibility(View.GONE);
                    mCommentRecyclerView.setVisibility(View.VISIBLE);
                    moreless.setText("Show less");
                }
            }
        });
    }

    private void iniRvComment()
    {

        mCommentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        twomCommentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference commentRef = firebaseDatabase.getReference(COMMENT_KEY).child(PostKey);
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                mComment = new ArrayList<>();
                twomComment=new ArrayList<>();
                ArrayList<DataSnapshot> dataSnapshotArrayList=new ArrayList<>();
                ArrayList<DataSnapshot> reversedataSnapshotArrayList=new ArrayList<>();
                for (DataSnapshot snap:snapshot.getChildren())
                {
                    dataSnapshotArrayList.add(snap);
                }

                for(int i=dataSnapshotArrayList.size()-1;i>=0;i--)
                {
                    reversedataSnapshotArrayList.add(dataSnapshotArrayList.get(i));
                }

                for(DataSnapshot snap: reversedataSnapshotArrayList)
                {
                    Comment comment = snap.getValue(Comment.class);
                    mComment.add(comment);

                    if(mComment.size()==1)
                    {
                        twomComment.add(mComment.get(0));
                    }
                    if(mComment.size()==2)
                    {
                        twomComment.add(mComment.get(1));
                    }
                    if(mComment.size()==3)
                    {
                        moreless.setVisibility(View.VISIBLE);
                    }
                }

                commentAdapter = new CommentAdapter(getApplicationContext(),mComment);
                mCommentRecyclerView.setAdapter(commentAdapter);

                twocommentAdapter=new CommentAdapter(getApplicationContext(),twomComment);
                twomCommentRecyclerView.setAdapter(twocommentAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private String timeStampToString(long time) {

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy",calendar).toString();
        return date;
    }

}