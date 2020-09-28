package com.example.foodhouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodhouse.R;
import com.example.foodhouse.activity.CommentsUserActivity;
import com.example.foodhouse.activity.DetailActivity;
import com.example.foodhouse.activity.DetailMethodActivity;
import com.example.foodhouse.model.FoodData;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<FoodViewHolder> {

    private Context mContext;
    private List<FoodData> myFoodList;
    private int lastPosition = -1;

    public MyAdapter(Context mContext, List<FoodData> myFoodList) {
        this.mContext = mContext;
        this.myFoodList = myFoodList;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_veg_recipe_list,viewGroup,false);

        return new FoodViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodViewHolder foodViewHolder, int i) {

        Glide.with(mContext)
                .load(myFoodList.get(i).getItemImage())
                .into(foodViewHolder.imageView);



        foodViewHolder.mTitle.setText(myFoodList.get(i).getItemName());

        foodViewHolder.mComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("RecipeName",myFoodList.get(foodViewHolder.getAdapterPosition()).getItemName());
                intent.putExtra("Image",myFoodList.get(foodViewHolder.getAdapterPosition()).getItemImage());
                intent.putExtra("postKey",myFoodList.get(foodViewHolder.getAdapterPosition()).getPostKey());
                mContext.startActivity(intent);
            }
        });

        foodViewHolder.mMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailMethodActivity.class);
                intent.putExtra("RecipeName",myFoodList.get(foodViewHolder.getAdapterPosition()).getItemName());
                intent.putExtra("Image",myFoodList.get(foodViewHolder.getAdapterPosition()).getItemImage());
                intent.putExtra("Method",myFoodList.get(foodViewHolder.getAdapterPosition()).getItemMethod());
                // intent.putExtra("postKey",myFoodList.get(foodViewHolder.getAdapterPosition()).getPublisher());
                mContext.startActivity(intent);
            }
        });

        foodViewHolder.mDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CommentsUserActivity.class);
                intent.putExtra("RecipeName",myFoodList.get(foodViewHolder.getAdapterPosition()).getItemName());
                intent.putExtra("Image",myFoodList.get(foodViewHolder.getAdapterPosition()).getItemImage());
                intent.putExtra("Description",myFoodList.get(foodViewHolder.getAdapterPosition()).getItemDescription());
                mContext.startActivity(intent);
            }
        });

        setAnimation(foodViewHolder.itemView,i);

    }

    public void setAnimation(View viewToAnimate , int position) {

        if (position > lastPosition) {
            ScaleAnimation animation = new ScaleAnimation(0.0f,1.0f,0.0f,1.0f,
                    Animation.RELATIVE_TO_SELF,0.5f,
                    Animation.RELATIVE_TO_SELF,0.5f);
            animation.setDuration(1000);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return myFoodList.size();
    }

    public void filteredList(ArrayList<FoodData> filterList) {
        myFoodList = filterList;
        notifyDataSetChanged();

    }
}

 class FoodViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView mTitle;
    Button mDescription,mMethod,mComments;
    CardView mCardView;

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.ivImage);
        mTitle = itemView.findViewById(R.id.tvTitle);
        mDescription = itemView.findViewById(R.id.tvDescription);
        mMethod = itemView.findViewById(R.id.tvMethod);
        mComments = itemView.findViewById(R.id.tvComment);

        mCardView = itemView.findViewById(R.id.myCardView);

    }
}
