package com.ass2.i190582_i190534;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.nullness.qual.NonNull;

public class MusicLibrary extends AppCompatActivity {

    ImageView startAddPlaylist, dp;
    ImageButton profileBtn;
    ImageView like, search, listen_later;
    Button logout, messenger, music_library;
    FirebaseAuth mAuth;

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

        messenger = findViewById(R.id.messenger);
        messenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MessengerPage.class);
                startActivity(intent);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                finish();
                Intent switchActivityIntent = new Intent(getApplicationContext(), Signup.class);
                startActivity(switchActivityIntent);
            }
        });
        music_library = findViewById(R.id.song_library);
        music_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MusicLibrary.this, SongsLibrary.class);
                startActivity(intent);
            }
        });

        dp = findViewById(R.id.dp);
        FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getUid()).child("profile_pic").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String dpURL = snapshot.getValue(String.class);
                Picasso.get().load(dpURL).into(dp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
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