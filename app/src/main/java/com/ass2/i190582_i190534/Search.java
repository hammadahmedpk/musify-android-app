package com.ass2.i190582_i190534;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Search extends AppCompatActivity {
    ImageView backButton;
    ImageView like, search, add, listen_later;
    RecyclerView recyclerView;
    SongsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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

        SearchView searchView = (SearchView) findViewById(R.id.searchText); // inititate a search view
        CharSequence query = searchView.getQuery();
        searchView.setQueryHint("Search Songs");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchSongs(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchSongs(s);
                return false;
            }
        });

        FirebaseRecyclerOptions <Song> options =
                new FirebaseRecyclerOptions.Builder<Song>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Songs").orderByChild("title"), Song.class)
                        .build();

        adapter = new SongsAdapter(options);
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
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Songs").orderByChild("title").startAt(s).endAt(s + "~"), Song.class)
                        .build();

        adapter = new SongsAdapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}