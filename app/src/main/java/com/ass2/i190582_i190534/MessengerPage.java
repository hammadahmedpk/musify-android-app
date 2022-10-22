package com.ass2.i190582_i190534;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.ReadOnlyBufferException;

public class MessengerPage extends AppCompatActivity {
    RecyclerView online_users_rv;
    OnlineUsersAdapter online_users_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_page);

        online_users_rv = findViewById(R.id.online_rv);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        online_users_rv.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<OnlineUsers> options =
                new FirebaseRecyclerOptions.Builder<OnlineUsers>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("firstName"), OnlineUsers.class)
                        .build();

        online_users_adapter = new OnlineUsersAdapter(options);
        online_users_rv.setAdapter(online_users_adapter) ;
    }

    @Override
    protected void onStart() {
        super.onStart();
        online_users_adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        online_users_adapter.stopListening();
    }
}