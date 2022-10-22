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

public class MessengerPageChatsAdapter extends  FirebaseRecyclerAdapter<MessengerPageChats, MessengerPageChatsAdapter.ViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MessengerPageChatsAdapter(@NonNull FirebaseRecyclerOptions<MessengerPageChats> options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messenger_page_chats_row, parent, false);
        return new MessengerPageChatsAdapter.ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull MessengerPageChats model) {
        holder.name.setText(model.getFirstName() + " " + model.getLastName());
        holder.last_message.setText(model.getLast_message());
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


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView dp;
        TextView name, last_message;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dp = itemView.findViewById(R.id.dp);
            name = itemView.findViewById(R.id.name);
            last_message = itemView.findViewById(R.id.last_message);
        }
    }
}
