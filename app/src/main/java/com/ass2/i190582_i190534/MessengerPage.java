package com.ass2.i190582_i190534;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;

import java.nio.ReadOnlyBufferException;
import java.util.Calendar;

public class MessengerPage extends AppCompatActivity {
    RecyclerView online_users_rv, chats_rv;
    OnlineUsersAdapter online_users_adapter;
    MessengerPageChatsAdapter chats_adapter;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    ImageView contacts;
    ImageView newChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_page);

        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        online_users_rv = findViewById(R.id.online_rv);
        DatabaseReference onlineUsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        Query onlineUsersQuery = onlineUsersRef.orderByChild("status").equalTo("true");

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        online_users_rv.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<OnlineUsers> options =
                new FirebaseRecyclerOptions.Builder<OnlineUsers>()
                        .setQuery(onlineUsersQuery, OnlineUsers.class)
                        .build();

        online_users_adapter = new OnlineUsersAdapter(options);
        online_users_rv.setAdapter(online_users_adapter);

        contacts = findViewById(R.id.contacts);
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FetchContacts.class);
                startActivity(intent);
            }
        });

    //  Chats RVs
        chats_rv = findViewById(R.id.chats_rv);
        chats_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        FirebaseRecyclerOptions <MessengerPageChats> chat_options =
                new FirebaseRecyclerOptions.Builder<MessengerPageChats>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("firstName"), MessengerPageChats.class)
                        .build();

        chats_adapter = new MessengerPageChatsAdapter(chat_options);
        chats_rv.setAdapter(chats_adapter);

        newChat = findViewById(R.id.newChat);
        newChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ChatDialogueBox dialog = new ChatDialogueBox();
                //dialog.onCreateDialog();
                new ChatDialogueBox().show(getSupportFragmentManager(), "");
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        online_users_adapter.startListening();
        chats_adapter.startListening();
        // For Online & Offline Status
        OnlineStatus.updateUserStatus();
    }

    @Override
    protected void onStop() {
        super.onStop();
        online_users_adapter.stopListening();
        chats_adapter.stopListening();
    }
}