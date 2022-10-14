package com.ass2.i190582_i190534;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class MusicLibrary extends AppCompatActivity {

    ImageView startAddPlaylist;
    ImageButton profileBtn;
    ImageView like, search, listen_later;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_library);

        final DrawerLayout drawer = findViewById(R.id.drawer);
        ImageButton sidebar = (ImageButton) findViewById(R.id.sidebar);

        sidebar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });

        startAddPlaylist = findViewById(R.id.add);
        startAddPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddPlaylist();
            }
        });

        profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startProfileActivity();
            }
        });

        like = findViewById(R.id.like);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLikeActivity();
            }
        });

        search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSearchActivity();
            }
        });

        listen_later = findViewById(R.id.listen_later);
        listen_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startListenLaterActivity();
            }
        });
    }
    private void startAddPlaylist() {
        Intent switchActivityIntent = new Intent(this, AddPlaylist.class);
        startActivity(switchActivityIntent);
    }
    private void startLikeActivity(){
        Intent switchActivityIntent = new Intent(this, Liked.class);
        startActivity(switchActivityIntent);
    }
    private void startSearchActivity(){
        Intent switchActivityIntent = new Intent(this, Search.class);
        startActivity(switchActivityIntent);
    }
    private void startListenLaterActivity(){
        Intent switchActivityIntent = new Intent(this, ListenLater.class);
        startActivity(switchActivityIntent);
    }
    private void startProfileActivity(){
        Intent switchActivityIntent = new Intent(this, Profile.class);
        startActivity(switchActivityIntent);
    }
}