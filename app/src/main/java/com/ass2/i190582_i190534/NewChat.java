package com.ass2.i190582_i190534;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class NewChat extends AppCompatActivity {

    RecyclerView recycle_view;
    NewChatAdapter new_chats_adapter;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        recycle_view = findViewById(R.id.recycle_view);
        recycle_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("firstName"), User.class)
                        .build();

        new_chats_adapter = new NewChatAdapter(options);
        recycle_view.setAdapter(new_chats_adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new_chats_adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        new_chats_adapter.stopListening();
    }
}