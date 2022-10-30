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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GroupChat extends AppCompatActivity {

    FirebaseDatabase db;
    FirebaseAuth mAuth;
    ImageView dp;
    TextView name;
    TextView status;
    EditText etMessage;
    ImageButton sendButton;
    RecyclerView chatRecyclerView;

    //ImageButton sendPic;
    //
    //ProgressDialog loadingBar;
    //
    //StorageTask uploadTask;
    //Uri uri;
    //String url;
    //
    //String sendRoom, recvRoom;
    //String sendID, recvID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.name);
        dp = findViewById(R.id.dp);
        status = findViewById(R.id.status);
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        etMessage = findViewById(R.id.etMessage);

        final ArrayList<MessageModel> messageModels = new ArrayList<MessageModel>();

        final String senderID = FirebaseAuth.getInstance().getUid();

        //loadingBar = new ProgressDialog(this);

        final ChatAdapter adapter = new ChatAdapter(messageModels, this, senderID, "group");
        chatRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(layoutManager);

        db.getReference().child("Group Chats")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageModels.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            MessageModel model = dataSnapshot.getValue(MessageModel.class);
                            messageModels.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String message = etMessage.getText().toString();
                final MessageModel model = new MessageModel(senderID, message);

                Date dataAndTime = Calendar.getInstance().getTime();
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
                String time = timeFormat.format(dataAndTime);
                model.setTimestamp(time);
                model.setType("text");

                String randomID = db.getReference().child("Group Chats").push().getKey();
                model.setMessageID(randomID);
                etMessage.setText(""); // So that edittext gets empty after a message has been sent

                db.getReference().child("Group Chats")
                        .child(randomID).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            }
                        });
            }
        });

        // To send image
        //sendPic = findViewById(R.id.sendPic);
        //sendPic.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Intent intent = new Intent();
        //        //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        //        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
        //            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        //        }
        //        intent.setAction(Intent.ACTION_GET_CONTENT);
        //        intent.setType("image/*");
        //        startActivityForResult(intent.createChooser(intent, "Select Image"), 100);
        //    }
        //});

    }
    //@Override
    //protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    //    super.onActivityResult(requestCode, resultCode, data);
    //    //if(requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null){
    //    if(requestCode == 100 && resultCode == RESULT_OK){
    //        if(data.getClipData() != null){
    //
    //            loadingBar.setTitle("Sending Image");
    //            loadingBar.setMessage("Please wait, your image is sending");
    //            loadingBar.setCanceledOnTouchOutside(false);
    //            loadingBar.show();
    //
    //            int x = data.getClipData().getItemCount();
    //            for(int i = 0; i < x; i++){
    //                //uri = data.getData();
    //                uri = data.getClipData().getItemAt(i).getUri();
    //                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Image Messages");
    //                final String randomID = db.getReference().child("Group Chats").push().getKey();
    //                DatabaseReference senderRef = db.getReference().child("Group Chats").child(randomID);
    //
    //                StorageReference filePath = storageReference.child(randomID + "." + "jpg");
    //
    //                uploadTask = filePath.putFile(uri);
    //                uploadTask.continueWithTask(new Continuation() {
    //                    @Override
    //                    public Object then(@NonNull Task task) throws Exception {
    //                        if(!task.isSuccessful()){
    //                            throw task.getException();
    //                        }
    //                        return filePath.getDownloadUrl();
    //                    }
    //                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
    //                    @Override
    //                    public void onComplete(@NonNull Task <Uri> task) {
    //                        if(task.isSuccessful()){
    //                            Uri downloadUrl = task.getResult();
    //                            url = downloadUrl.toString();
    //
    //                            final MessageModel model = new MessageModel(sendID, url);
    //                            Date dataAndTime = Calendar.getInstance().getTime();
    //                            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
    //                            String time = timeFormat.format(dataAndTime);
    //                            model.setMessageID(randomID);
    //                            model.setTimestamp(time);
    //                            model.setType("image");
    //
    //                            senderRef.setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
    //                                @Override
    //                                public void onSuccess(Void unused) {
    //                                    loadingBar.dismiss();
    //                                    Toast.makeText(getApplicationContext(), "File uploaded succesfully!", Toast.LENGTH_SHORT).show();
    //                                }
    //                            });
    //                        }
    //                    }
    //                });
    //
    //            }
    //        }
    //    }
    //}
}