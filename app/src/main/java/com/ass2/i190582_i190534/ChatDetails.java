package com.ass2.i190582_i190534;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class ChatDetails extends AppCompatActivity {

    FirebaseDatabase db;
    FirebaseAuth mAuth;
    ImageView dp;
    TextView name;
    TextView status;
    EditText etMessage;

    RecyclerView chatRecyclerView;
    ImageButton sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);

        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.name);
        dp = findViewById(R.id.dp);
        status = findViewById(R.id.status);

        final String senderID = mAuth.getUid();
        String receiverID = getIntent().getStringExtra("userID");
        String person_name = getIntent().getStringExtra("name");
        String profile_pic = getIntent().getStringExtra("profile_pic");

        name.setText(person_name);
        Picasso.get().load(profile_pic).into(dp);

        // Check for updating last seen and online status
        updateLastSeen(receiverID);

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        final ArrayList<MessageModel> messageModels = new ArrayList<>();
        final ChatAdapter chatAdapter = new ChatAdapter(messageModels, this);
        chatRecyclerView.setAdapter(chatAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(layoutManager);

        final String senderRoom = senderID + receiverID;
        final String receiverRoom = receiverID + senderID;

        // Setting the Incoming and Outgoing messages in RecyclerView
        db.getReference().child("Chats").child(senderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModels.clear();
                // Looping through all the messages
                for(DataSnapshot snapshot1: snapshot.getChildren())
                {
                    MessageModel model = snapshot1.getValue(MessageModel.class);
                    messageModels.add(model);
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Sending the message and updating it in database on both sender and receiverend
        sendButton = findViewById(R.id.sendButton);
        etMessage = findViewById(R.id.etMessage);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = etMessage.getText().toString();
                final MessageModel model = new MessageModel(senderID, message);
                model.setTimestamp(new Date().getTime());
                etMessage.setText(""); // So that edittext gets empty after a message has been sent

                // Making a new child for chats
                // Sender Stuff
                db.getReference().child("Chats").child(senderRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // Reciever Stuff
                        db.getReference().child("Chats").child(receiverRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                });


            }
        });

    }

    public void updateLastSeen(String receiverID){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Users").child(receiverID);
        DatabaseReference statusRef = FirebaseDatabase.getInstance().getReference().child("Users").child(receiverID).child("status");

        statusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String statusAns = snapshot.getValue(String.class);
                if(statusAns.equals("true")){
                    status.setText("Online");
                }
                else
                {
                    dbRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //User user = snapshot.getValue(User.class);
                            Map<String, Object> map = (Map<String, Object>) snapshot.getValue();

                            status.setText("Last seen at " + map.get("last_seen"));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}