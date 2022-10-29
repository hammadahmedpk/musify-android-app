package com.ass2.i190582_i190534;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder>{

    ArrayList<Comment> commentModels;
    Context context;

    public CommentsAdapter(ArrayList<Comment> commentModels, Context context) {
        this.commentModels = commentModels;
        this.context = context;
    }


    @Override
    public int getItemCount() {
        return commentModels.size();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_comment_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       // holder.comment.setText(getComment());
        Comment commentModel = commentModels.get(position);
        holder.comment.setText(commentModel.getComment());
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView comment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comment = itemView.findViewById(R.id.comment_line);
        }
    }
}
