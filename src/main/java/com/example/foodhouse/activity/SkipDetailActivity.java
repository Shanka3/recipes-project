package com.example.foodhouse.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodhouse.R;
import com.example.foodhouse.adapter.CommentAdapter;
import com.example.foodhouse.model.Comment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SkipDetailActivity extends AppCompatActivity {

    RecyclerView mCommentRecyclerView;
    RecyclerView twomCommentRecyclerView;
    List<Comment> mComment,twomComment;

    TextView foodName;
    ImageView foodImage;

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
        setContentView(R.layout.activity_skip_detail);

        getSupportActionBar().hide();


        foodName = (TextView)findViewById(R.id.txtRecipeName);
        foodImage = (ImageView)findViewById(R.id.ivImage2);
        moreless=(Button)findViewById(R.id.moreless);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        mCommentRecyclerView = (RecyclerView)findViewById(R.id.rv_Comment);
        twomCommentRecyclerView = (RecyclerView)findViewById(R.id.two_rv_Comment);
        Bundle bundle = getIntent().getExtras();

        if (bundle!=null){
            foodName.setText(bundle.getString("RecipeName"));
            Glide.with(this)
                    .load(bundle.getString("Image"))
                    .into(foodImage);
        }
        PostKey = getIntent().getExtras().getString("postKey");

        iniRvComment();

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

    private void iniRvComment() {

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
                        twomComment.add(mComment.get(2));
                    }
                    if(mComment.size()==4)
                    {
                        twomComment.add(mComment.get(3));
                    }
                    if(mComment.size()==5)
                    {
                        twomComment.add(mComment.get(4));
                    }
                    if(mComment.size()==6)
                    {
                        twomComment.add(mComment.get(5));
                    }
                    if(mComment.size()==7)
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
}