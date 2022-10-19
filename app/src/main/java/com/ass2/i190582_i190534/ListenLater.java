package com.ass2.i190582_i190534;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ListenLater extends AppCompatActivity {

    Button playlistBtn;
    Button musicBtn;
    ImageView like, search, add, listen_later;
    TextView text1,text3, sideText1, sideText2;
    ImageView image1, image2, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_later);

        playlistBtn = findViewById(R.id.playlistBtn);
        musicBtn = findViewById(R.id.musicBtn);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);

        text1 = findViewById(R.id.text1);
        text3 = findViewById(R.id.text3);

        sideText1 = findViewById(R.id.sideText1);
        sideText2 = findViewById(R.id.sideText2);

        playlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playlistBtn.setBackgroundResource(R.drawable.rounded_yellow_button);
                image1.setBackgroundResource(R.drawable.playlist_screen5);
                image2.setBackgroundResource(R.drawable.playlist_screen5);
                text1.setText("Playlist 1");
                text3.setText("Playlist 2");

                sideText1.setText("Tracks: 8");
                sideText2.setText("Tracks: 3");
                musicBtn.setBackgroundResource(R.drawable.comment_box);
            }
        });

        musicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicBtn.setBackgroundResource(R.drawable.rounded_yellow_button);
                image1.setBackgroundResource(R.drawable.despacito);
                image2.setBackgroundResource(R.drawable.despacito);

                text1.setText("Song Name");
                text3.setText("Song Name");

                sideText1.setText("3:01");
                sideText2.setText("0:24");
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
    }
}