package com.ass2.i190582_i190534;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.ClipData;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SongToPlaylistAdapter  extends FirebaseRecyclerAdapter<Song, SongToPlaylistAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    ArrayList<Song> ls = new ArrayList<>();
    public SongToPlaylistAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_to_playlist_row, parent, false);
        return new myViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Song model) {

        holder.title.setText(model.getTitle());
        holder.artist.setText("Artist");
        Picasso.get().load(model.getImage()).into(holder.image);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), AddPlaylist.class);
                intent.putExtra("title",model.getTitle());
                intent.putExtra("url",model.getUrl());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }







//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(holder.itemView.getContext(), PlaySong.class);
//                intent.putExtra("title", model.getTitle());
//                intent.putExtra("url", model.getUrl());
//                holder.itemView.getContext().startActivity(intent);
//            }
//        });





    class myViewHolder extends RecyclerView.ViewHolder{

        TextView title, artist,add;
        ImageView image;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image1);
            title = itemView.findViewById(R.id.text1);
            artist = itemView.findViewById(R.id.text2);
            add = itemView.findViewById(R.id.checkbox);

        }
    }
}
