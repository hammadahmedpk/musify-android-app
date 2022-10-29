package com.ass2.i190582_i190534;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CreateProfile extends AppCompatActivity {

    ImageView dp, dpRound;
    EditText firstName, lastName, bio;
    ImageButton male, female, other;
    Button createProfile;
    FirebaseDatabase db;
    FirebaseAuth mAuth;
    FirebaseStorage storage;
    StorageReference storageRef;

    Uri dpp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        dp = findViewById(R.id.dp);
        dpRound = findViewById(R.id.dpRound);
        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(i, "Choose your DP"),
                        100);
            }
        });

        db = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mAuth = FirebaseAuth.getInstance();

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        bio = findViewById(R.id.bio);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        other = findViewById(R.id.other);
        createProfile = findViewById(R.id.createProfile);

        final String[] gender = new String[1];

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender[0] = "male";
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender[0] = "female";
            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender[0] = "other";
            }
        });

        createProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(firstName.getText().toString(),lastName.getText().toString(), gender[0],bio.getText().toString(),mAuth.getUid().toString(), "Hello World!", "","false","");
                db.getReference().child("Users").child(mAuth.getUid()).setValue(user);
                storageRef.child("profile_pics").child(mAuth.getUid()).putFile(dpp).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    db.getReference().child("Users").child(mAuth.getUid()).child("profile_pic").setValue(uri.toString());
                                    Intent intent = new Intent(getApplicationContext(), MusicLibrary.class);
                                    startActivity(intent);
                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(CreateProfile.this, "Failed to upload image URL to database!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK) {
            dpp = data.getData();
            dpRound.setImageURI(dpp);
            dp.setBackground(null);
        }
    }
}