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

public class ListenLaterAdapter extends FirebaseRecyclerAdapter<Song, ListenLaterAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public ListenLaterAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }

    @NonNull
    @Override
    public ListenLaterAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listen_later_row, parent, false);
        return new ListenLaterAdapter.myViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ListenLaterAdapter.myViewHolder holder, int position, @NonNull Song model) {
        Picasso.get().load(model.getImage()).into(holder.image);
        holder.song_name.setText(model.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), PlaySong.class);
                intent.putExtra("title", model.getTitle());
                intent.putExtra("url", model.getUrl());
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView song_name;
        ImageView image;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image1);
            song_name = itemView.findViewById(R.id.song_name);

        }
    }
}
