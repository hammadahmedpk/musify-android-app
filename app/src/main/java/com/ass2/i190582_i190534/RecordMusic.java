package com.ass2.i190582_i190534;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;

public class RecordMusic extends AppCompatActivity {
    ImageView backButton;
    ImageView search;
    ImageView pause;
    ImageView record;

    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String fileName = null;
    private MediaRecorder recorder = null;
    private MediaPlayer player = null;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_music);
        // Record to the external cache directory for visibility
        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "/audiorecordtest.3gp";

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
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
                    startPlaying();
                }
                else
                {
                    pause.setBackgroundResource(R.drawable.songname_pause_screen5);
                    stopPlaying();
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
                    startRecording();
                }
                else
                {
                    record.setBackgroundResource(R.drawable.circle_page11);
                    stopRecording();
                }
            }
        });
    }
    private void startSearchActivity() {
        Intent switchActivityIntent = new Intent(this, Search.class);
        startActivity(switchActivityIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }
    private void startPlaying() {
        Toast.makeText(RecordMusic.this, "Recording!", Toast.LENGTH_SHORT).show();

        player = new MediaPlayer();
        try {
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        player.release();
        player = null;
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

//Have to remove and check
    public void onStop() {
        super.onStop();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }

        if (player != null) {
            player.release();
            player = null;
        }
    }
}
