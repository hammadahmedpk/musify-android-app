package com.ass2.i190582_i190534;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class RecordMusic extends AppCompatActivity {
    ImageView backButton;
    ImageView search;
    ImageView pause;
    ImageView record;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_music);
        backButton = findViewById(R.id.backarrow);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSearchActivity();
            }
        });

        // To Change Play button to pause on click
        pause = findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toggling b/w play and pause
                if(pause.getBackground().getConstantState()==getDrawable(R.drawable.songname_pause_screen5).getConstantState())
                {
                    pause.setBackgroundResource(R.drawable.pause_page14);
                }
                else
                {
                    pause.setBackgroundResource(R.drawable.songname_pause_screen5);
                }
            }
        });

        // To Change Play button to pause on click
        record = findViewById(R.id.record);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toggling b/w play and pause
                if(record.getBackground().getConstantState()==getDrawable(R.drawable.circle_page11).getConstantState())
                {
                    record.setBackgroundResource(R.drawable.recorded);
                }
                else
                {
                    record.setBackgroundResource(R.drawable.circle_page11);
                }
            }
        });
    }
    private void startSearchActivity() {
        Intent switchActivityIntent = new Intent(this, Search.class);
        startActivity(switchActivityIntent);
    }
}