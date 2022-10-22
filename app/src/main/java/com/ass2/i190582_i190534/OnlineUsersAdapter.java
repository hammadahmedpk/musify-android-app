package com.ass2.i190582_i190534;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class OnlineUsersAdapter extends FirebaseRecyclerAdapter<OnlineUsers, OnlineUsersAdapter.myViewHolder> {

    public OnlineUsersAdapter(@NonNull FirebaseRecyclerOptions<OnlineUsers> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull OnlineUsers model) {
        // Don't show offline users
//        if(model.getStatus().equals("true")){
            Picasso.get().load(model.getProfile_pic()).into(holder.image);
//        }
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.online_users_row, parent, false);
        return new OnlineUsersAdapter.myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);

        }
    }
}
