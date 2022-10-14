package com.ass2.i190582_i190534;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class UploadMusic extends AppCompatActivity {
    ImageView backButton;
    Button recordBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_music);

        backButton = findViewById(R.id.backarrow);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recordBtn = findViewById(R.id.recordBtn);
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRecordMusic();
            }
        });
    }
    private void startRecordMusic() {
        Intent switchActivityIntent = new Intent(this, com.ass2.i190582_i190534.RecordMusic.class);
        startActivity(switchActivityIntent);
    }
}