package com.ass2.i190582_i190534;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Liked extends AppCompatActivity {

    Button playlistBtn;
    Button musicBtn;
    ImageView like, search, add, listen_later;
    TextView text1,text3, sideText1, sideText2;
    ImageView image1, image2, backButton;
    RecyclerView recyclerView;
    LikedMusicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked);

        playlistBtn = findViewById(R.id.playlistBtn);
        musicBtn = findViewById(R.id.musicBtn);


        playlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playlistBtn.setBackgroundResource(R.drawable.rounded_yellow_button);
                musicBtn.setBackgroundResource(R.drawable.comment_box);
            }
        });

        musicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicBtn.setBackgroundResource(R.drawable.rounded_yellow_button);
                playlistBtn.setBackgroundResource(R.drawable.comment_box);
            }
        });

        backButton = findViewById(R.id.backarrow);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // For Switching Activities on Footer
        like = findViewById(R.id.like);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Liked.class);
                startActivity(intent);
            }
        });

        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddPlaylist.class);
                startActivity(intent);
            }
        });

        search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Search.class);
                startActivity(intent);
            }
        });

        listen_later = findViewById(R.id.listen_later);
        listen_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListenLater.class);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        FirebaseRecyclerOptions<Song> options =
                new FirebaseRecyclerOptions.Builder<Song>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Liked Songs").child(FirebaseAuth.getInstance().getUid()).orderByChild("title"), Song.class)
                        .build();
        Log.d("i dont care:\n\n\n\n\n", String.valueOf(options));
        adapter = new LikedMusicAdapter(options);
        recyclerView.setAdapter(adapter) ;

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void searchSongs(String s){

        FirebaseRecyclerOptions <Song> options =
                new FirebaseRecyclerOptions.Builder<Song>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Liked Songs").child(FirebaseAuth.getInstance().getUid()).orderByChild("title"), Song.class)
                        .build();

        adapter = new LikedMusicAdapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}