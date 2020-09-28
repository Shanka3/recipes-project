package com.example.foodhouse.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodhouse.R;
import com.example.foodhouse.model.Comment;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>  {

    private Context mContext;
    private List<Comment> mComment;


    public CommentAdapter(Context mContext, List<Comment> mComment) {
        this.mContext = mContext;
        this.mComment = mComment;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {

        holder.userComment.setText(mComment.get(position).getUserComments());
        holder.commentDate.setText(timeStampToString((long)mComment.get(position).getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return mComment.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

    TextView userComment,commentDate;

    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);

        userComment = itemView.findViewById(R.id.tvUserComments);
        commentDate = itemView.findViewById(R.id.comment_date);

    }
   }

    private String timeStampToString(long time) {

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("hh:mma",calendar).toString();
        date=date.toUpperCase();
        return date;
    }

}
