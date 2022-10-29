package com.ass2.i190582_i190534;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class NewChatAdapter extends FirebaseRecyclerAdapter<User, NewChatAdapter.ViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public NewChatAdapter(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NewChatAdapter.ViewHolder holder, int position, @NonNull User model) {
        holder.name.setText(model.getFirstName() + " " + model.getLastName());
        holder.bio.setText(model.getBio());
        Picasso.get().load(model.getProfile_pic()).into(holder.dp);

        // Sending the stuff to chat details activity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), ChatDetails.class);
                intent.putExtra("userID", model.getUser_id());
                intent.putExtra("profile_pic", model.getProfile_pic());
                intent.putExtra("name", model.getFirstName()+" "+model.getLastName());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public NewChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_chat_row, parent, false);
        return new NewChatAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, bio;
        ImageView dp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            bio = itemView.findViewById(R.id.bio);
            dp = itemView.findViewById(R.id.dp);
        }
    }
}
