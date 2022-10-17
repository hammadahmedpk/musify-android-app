package com.ass2.i190582_i190534;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadMusic extends AppCompatActivity {
    ImageView backButton, uploadMusic;
    Button recordBtn, uploadBtn;
    private final int PICK_AUDIO = 1;
    Uri AudioUri;
    FirebaseDatabase db;
    FirebaseAuth mAuth;
    FirebaseStorage storage;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_music);
        db = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        uploadMusic = findViewById(R.id.upload_music);
        backButton = findViewById(R.id.backarrow);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        uploadBtn = findViewById(R.id.uploadbutton);
        recordBtn = findViewById(R.id.recordBtn);
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRecordMusic();
            }
        });
        uploadMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent audio = new Intent();
                audio.setType("audio/*");
                audio.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(Intent.createChooser(audio, "Select Audio"), PICK_AUDIO);

            }
        });
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // Toast.makeText(UploadMusic.this, "Failed to upload image URL to database!", Toast.LENGTH_SHORT).show();
                storageRef.child("audios").child(mAuth.getUid()).child("uploaded_songs").child("song01").putFile(AudioUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UploadMusic.this, "Music has been uploaded!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UploadMusic.this, "Failed to upload image URL to database!", Toast.LENGTH_SHORT).show();
                       }
                    }
                });
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_AUDIO && resultCode == RESULT_OK) {
            AudioUri = data.getData();
            Toast.makeText(UploadMusic.this, "Audio has been added!", Toast.LENGTH_SHORT).show();

        }
    }
    private void startRecordMusic() {
        Intent switchActivityIntent = new Intent(this, com.ass2.i190582_i190534.RecordMusic.class);
        startActivity(switchActivityIntent);
    }
}