package com.ass2.i190582_i190534;

import android.content.Intent;
import android.util.Log;
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

public class SongsAdapter extends FirebaseRecyclerAdapter<Song, SongsAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public SongsAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_song_row, parent, false);
        return new myViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Song model) {

        holder.title.setText(model.getTitle());
        holder.artist.setText("Artist");
        Picasso.get().load(model.getImage()).into(holder.image);
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

        TextView title, artist;
        ImageView image;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image1);
            title = itemView.findViewById(R.id.text1);
            artist = itemView.findViewById(R.id.text2);

        }
    }
}
