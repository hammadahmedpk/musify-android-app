package com.ass2.i190582_i190534;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    ImageView like, search, add, listen_later;
    Button recordBtn, uploadBtn;
    EditText title, genre, description;
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
        title = findViewById(R.id.title);
        genre = findViewById(R.id.genre);
        description = findViewById(R.id.description);

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
                Song song = new Song(title.getText().toString(), genre.getText().toString(), description.getText().toString(), "","");
                Toast.makeText(UploadMusic.this, "Uploading Song!", Toast.LENGTH_SHORT).show();
                storageRef.child("audios").child(mAuth.getUid()).child("uploaded_songs").child(song.title).putFile(AudioUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    db.getReference().child("Songs").child(song.title).child("url").setValue(uri.toString());
                                }
                            });
                            Toast.makeText(UploadMusic.this, "Music has been uploaded!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UploadMusic.this, "Failed to upload image URL to database!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                db.getReference().child("Songs").child(song.getTitle()).setValue(song);
                db.getReference().child("Songs").child(song.getTitle()).child("image").setValue("https://firebasestorage.googleapis.com/v0/b/musify-android-app.appspot.com/o/extras%2Fplaylist_screen5.png?alt=media&token=5a827bca-4d58-4e51-944e-db6beb225a7e");
            }
        });

        // For Switching Activities on Footer
        like = findViewById(R.id.like);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Liked.class);
                startActivity(intent);
            }
        });

        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddPlaylist.class);
                startActivity(intent);
            }
        });

        search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Search.class);
                startActivity(intent);
            }
        });

        listen_later = findViewById(R.id.listen_later);
        listen_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListenLater.class);
                startActivity(intent);
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_AUDIO && resultCode == RESULT_OK) {
            AudioUri = data.getData();
            //String name = getNameFromURI(AudioUri);
            ImageView upload_music = (ImageView) (findViewById(R.id.upload_music));
            upload_music.setBackgroundResource(R.drawable.music_uploaded_icon);
            Toast.makeText(UploadMusic.this, "Song has been added successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    private void startRecordMusic() {
        Intent switchActivityIntent = new Intent(this, com.ass2.i190582_i190534.RecordMusic.class);
        startActivity(switchActivityIntent);
    }

}