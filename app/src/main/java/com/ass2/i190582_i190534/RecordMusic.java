package com.ass2.i190582_i190534;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class RecordMusic extends AppCompatActivity {
    ImageView backButton;
    ImageView search;
    ImageView pause;
    ImageView record;
    Button upload;
    EditText name;
    SeekBar seekBar;

    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String fileName = null;
    private StorageReference strRef;
    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    private Handler mHandler = new Handler();
    FirebaseDatabase db;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_music);
        strRef = FirebaseStorage.getInstance().getReference();
        db = FirebaseDatabase.getInstance();
        seekBar = findViewById(R.id.seekbar);
        // Record to the external cache directory for visibility
        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "/recorded_audio.3gp";

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
        name = findViewById(R.id.playlist_name);
        upload = findViewById(R.id.upload_recording);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadMusic();
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

        RecordMusic.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (player != null) {
                    int mCurrentPosition = player.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                }
                mHandler.postDelayed(this, 1000);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (player != null && fromUser) {
                    player.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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

        player = new MediaPlayer();
        player.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        try {
            player.setDataSource(fileName);
            player.prepare(); // might take long! (for buffering, etc)
            player.start();
            seekBar.setMax(player.getDuration() / 1000);
        } catch (IOException e) {
            e.printStackTrace();
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



    private void uploadMusic(){
        Uri uri = Uri.fromFile(new File(fileName));
        strRef.child("audios").child(FirebaseAuth.getInstance().getUid()).child("uploaded_songs").child(name.getText().toString()).putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            db.getReference().child("Songs").child(name.getText().toString()).child("url").setValue(uri.toString());
                        }
                    });
                    Toast.makeText(RecordMusic.this, "Music has been uploaded!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RecordMusic.this, "Failed to upload image URL to database!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        db.getReference().child("Songs").child(name.getText().toString()).child("title").setValue(name.getText().toString());
        db.getReference().child("Songs").child(name.getText().toString()).child("image").setValue("https://firebasestorage.googleapis.com/v0/b/musify-android-app.appspot.com/o/extras%2Fplaylist_screen5.png?alt=media&token=5a827bca-4d58-4e51-944e-db6beb225a7e");
    }
    

}
