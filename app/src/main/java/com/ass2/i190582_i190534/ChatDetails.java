package com.ass2.i190582_i190534;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ChatDetails extends AppCompatActivity {

    FirebaseDatabase db;
    FirebaseAuth mAuth;
    ImageView dp;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);

        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.name);
        dp = findViewById(R.id.dp);

        String senderID = mAuth.getUid();
        String receiverID = getIntent().getStringExtra("userID");
        String person_name = getIntent().getStringExtra("name");
        String profile_pic = getIntent().getStringExtra("profile_pic");

        name.setText(person_name);
        Picasso.get().load(profile_pic).into(dp);

    }
}