package com.ass2.i190582_i190534;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

public class AddPlaylist extends AppCompatActivity {
    ImageView backButton, upload_playlist;
    ImageView like, search, add, listen_later;
    Button addPlaylist,next;
    EditText desc, name;
    FirebaseDatabase db ;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_playlist);
        db = FirebaseDatabase.getInstance();
        backButton = findViewById(R.id.backarrow);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addPlaylist = findViewById(R.id.addPlaylist);
        addPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startUploadMusic();
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
        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UploadMusic.class);
                startActivity(intent);
            }
        });
        upload_playlist = findViewById(R.id.upload_playlist);
        upload_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddSongToPlaylist.class);
                startActivity(intent);
            }
        });
        name = findViewById(R.id.playlist_name);
        desc = findViewById(R.id.description);
        addPlaylist=findViewById(R.id.addPlaylist);
        addPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = getIntent().getExtras().getString("url");
                String title = getIntent().getExtras().getString("title");
                db.getReference().child("Playlists").child(name.getText().toString()).child(title).child("title").setValue(title);
                db.getReference().child("Playlists").child(name.getText().toString()).child(title).child("url").setValue(url);
                Toast.makeText(AddPlaylist.this, "Creating Playlist", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void startUploadMusic() {

        Intent switchActivityIntent = new Intent(this, com.ass2.i190582_i190534.UploadMusic.class);
        startActivity(switchActivityIntent);
    }


}