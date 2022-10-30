package com.ass2.i190582_i190534;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.screenshotdetection.ScreenshotDetectionDelegate;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class ChatDetails extends AppCompatActivity {

    FirebaseDatabase db;
    FirebaseAuth mAuth;
    ImageView dp;
    TextView name;
    TextView status;
    EditText etMessage;
    ImageButton sendPic;

    ProgressDialog loadingBar;

    RecyclerView chatRecyclerView;
    ImageButton sendButton;
    StorageTask uploadTask;
    Uri uri;
    String url;

    String sendRoom, recvRoom;
    String sendID, recvID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);

        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.name);
        dp = findViewById(R.id.dp);
        status = findViewById(R.id.status);

        loadingBar = new ProgressDialog(this);

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
        final ChatAdapter chatAdapter = new ChatAdapter(messageModels, this, senderID, receiverID);
        chatRecyclerView.setAdapter(chatAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(layoutManager);

        final String senderRoom = senderID + receiverID;
        final String receiverRoom = receiverID + senderID;

        sendID = senderID;
        recvID = receiverID;

        sendRoom = senderRoom;
        recvRoom = receiverRoom;



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
                Date dataAndTime = Calendar.getInstance().getTime();
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
                String time = timeFormat.format(dataAndTime);
                model.setTimestamp(time);
                model.setType("text");

                String randomID = db.getReference().child("Chats").push().getKey();
                model.setMessageID(randomID);
                etMessage.setText(""); // So that edittext gets empty after a message has been sent

                // Making a new child for chats
                // Sender Stuff
                //db.getReference().child("Chats").child(senderRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                db.getReference().child("Chats").child(senderRoom).child(randomID).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // Reciever Stuff
                        //String recvRandomID = db.getReference().child("Chats").push().getKey();
                        //model.setMessageID(recvRandomID);
                        db.getReference().child("Chats").child(receiverRoom).child(randomID).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            }
                        });
                    }
                });


            }
        });

        // To send image
        sendPic = findViewById(R.id.sendPic);
        sendPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                }
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "Select Image"), 100);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if(requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null){
        if(requestCode == 100 && resultCode == RESULT_OK){
            if(data.getClipData() != null){

                loadingBar.setTitle("Sending Image");
                loadingBar.setMessage("Please wait, your image is sending");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                int x = data.getClipData().getItemCount();
                for(int i = 0; i < x; i++){
                    //uri = data.getData();
                    uri = data.getClipData().getItemAt(i).getUri();
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Image Messages");
                    final String randomID = db.getReference().child("Chats").push().getKey();
                    DatabaseReference senderRef = db.getReference().child("Chats").child(sendRoom).child(randomID);
                    DatabaseReference receiverRef = db.getReference().child("Chats").child(recvRoom).child(randomID);

                    StorageReference filePath = storageReference.child(randomID + "." + "jpg");

                    uploadTask = filePath.putFile(uri);
                    uploadTask.continueWithTask(new Continuation() {
                        @Override
                        public Object then(@NonNull Task task) throws Exception {
                            if(!task.isSuccessful()){
                                throw task.getException();
                            }
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task <Uri> task) {
                            if(task.isSuccessful()){
                                Uri downloadUrl = task.getResult();
                                url = downloadUrl.toString();

                                final MessageModel model = new MessageModel(sendID, url);
                                Date dataAndTime = Calendar.getInstance().getTime();
                                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
                                String time = timeFormat.format(dataAndTime);
                                model.setMessageID(randomID);
                                model.setTimestamp(time);
                                model.setType("image");

                                //String randomID = db.getReference().child("Chats").push().getKey();
                                //model.setMessageID(randomID);

                                senderRef.setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        receiverRef.setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                loadingBar.dismiss();
                                                Toast.makeText(ChatDetails.this, "File uploaded succesfully!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    });

                }
            }
        }
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