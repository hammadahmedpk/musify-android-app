package com.ass2.i190582_i190534;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class SongComments extends AppCompatActivity {
    EditText comment;
    ImageView commentBtn;
    FirebaseDatabase db;
    FirebaseAuth mAuth;
    String title;
    RecyclerView commentsRecyclerView;
    TextView songTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_comments);

        commentsRecyclerView = findViewById(R.id.rv);
        songTitle = findViewById(R.id.song_title);
        db = FirebaseDatabase.getInstance();
        title = getIntent().getStringExtra("song");
        mAuth = FirebaseAuth.getInstance();
        final String senderID = mAuth.getUid();
        comment = findViewById(R.id.postcomment);
        commentBtn = findViewById(R.id.commentbutton);

        final ArrayList<Comment> comments = new ArrayList<>();
        final CommentsAdapter cAdapter = new CommentsAdapter(comments, this);
        commentsRecyclerView.setAdapter(cAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        commentsRecyclerView.setLayoutManager(layoutManager);

        // Setting the Incoming and Outgoing messages in RecyclerView
        db.getReference().child("Songs").child("chaap").child("Comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comments.clear();
                // Looping through all the messages
                for(DataSnapshot snapshot1: snapshot.getChildren())
                {
                    Comment model =  snapshot1.getValue(Comment.class);
                    comments.add(model);
                }
                cAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songTitle.setText(title);
                String message = comment.getText().toString();
                Comment model = new Comment();
                model.setComment(message);
                model.setSong(title);
                model.setUser(senderID);
                comment.setText("");
                db.getReference().child("Songs").child(title).child("Comments").child(senderID+new Date().getTime()).setValue(model);
            }
        });
    }
}