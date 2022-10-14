package com.ass2.i190582_i190534;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AddPlaylist extends AppCompatActivity {
    ImageView backButton;
    Button addPlaylist;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_playlist);

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
    }
    private void startUploadMusic() {
        Intent switchActivityIntent = new Intent(this, com.ass2.i190582_i190534.UploadMusic.class);
        startActivity(switchActivityIntent);
    }
}