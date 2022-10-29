package com.ass2.i190582_i190534;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class EditProfile extends AppCompatActivity {

    ImageView backButton;
    ImageView dp;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        backButton = findViewById(R.id.backarrow);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dp = findViewById(R.id.dp);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().getReference("Users").child(Objects.requireNonNull(mAuth.getUid())).child("profile_pic").addValueEventListener(new ValueEventListener() {
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
}