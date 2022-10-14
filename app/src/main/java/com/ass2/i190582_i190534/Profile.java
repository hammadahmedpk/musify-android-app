package com.ass2.i190582_i190534;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    ImageView backButton;
    ImageView editProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        backButton = findViewById(R.id.backarrow);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editProfile = findViewById(R.id.editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEditProfileActivity();
            }
        });
    }
    private void startEditProfileActivity() {
        Intent switchActivityIntent = new Intent(this, com.ass2.i190582_i190534.EditProfile.class);
        startActivity(switchActivityIntent);
    }
}