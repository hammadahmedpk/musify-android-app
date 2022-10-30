package com.ass2.i190582_i190534;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class ChatAdapter extends RecyclerView.Adapter{

    ArrayList<MessageModel> messageModels;
    Context context;
    String senderID;
    String receiverID;

    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String senderID, String receiverID) {
        this.messageModels = messageModels;
        this.context = context;
        this.senderID = senderID;
        this.receiverID = receiverID;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.sender_message, parent, false);
            return new SenderViewHolder(view);
        }
        // Receiver
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.receiver_message, parent, false);
            return new ReceiverViewHolder(view);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(messageModels.get(position).getuID().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;
        }
        else{
            return RECEIVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModel = messageModels.get(position);

        if(holder.getClass() ==  SenderViewHolder.class){
            if(messageModel.getType().equals("text"))
            {
                ((SenderViewHolder)holder).senderMsg.setText(messageModel.getMessage());
                ((SenderViewHolder)holder).senderTime.setText(messageModel.getTimestamp());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //MessageDialogueBox msg = new MessageDialogueBox();
                        ////Toast.makeText(context, messageModel.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        //new MessageDialogueBox().show(msg.getChildFragmentManager(),"");
                        String [] chats = {"Edit Message", "Delete Message"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Message Options")
                                .setItems(chats, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(chats[which].equals("Edit Message")){
                                            //Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
                                            final EditText txt = new EditText(context);
                                            txt.setText(messageModels.get(position).getMessage());
                                            new AlertDialog.Builder(context)
                                                    .setTitle("Edit Text")
                                                    .setView(txt)
                                                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                            String textMsg = txt.getText().toString();

                                                            // Updating the message in Database

                                                            // Updating in Sender Room
                                                            final String senderRoom = senderID + receiverID;
                                                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Chats").child(senderRoom).child(messageModels.get(position).getMessageID());
                                                            dbRef.child("message").setValue(textMsg);

                                                            // Updating from Receiver Room
                                                            final String receiverRoom = receiverID + senderID;
                                                            dbRef = FirebaseDatabase.getInstance().getReference("Chats").child(receiverRoom).child(messageModels.get(position).getMessageID());
                                                            dbRef.child("message").setValue(textMsg);
                                                        }
                                                    })
                                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                        }
                                                    })
                                                    .show();
                                        }
                                        else
                                        {
                                            // Deleting from Sender Room
                                            final String senderRoom = senderID + receiverID;
                                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Chats").child(senderRoom).child(messageModels.get(position).getMessageID());
                                            dbRef.removeValue();

                                            // Deleting from Receiver Room
                                            final String receiverRoom = receiverID + senderID;
                                            dbRef = FirebaseDatabase.getInstance().getReference("Chats").child(receiverRoom).child(messageModels.get(position).getMessageID());
                                            dbRef.removeValue();
                                        }
                                    }
                                });
                        builder.show();
                    }
                });
            }
            else if(messageModel.getType().equals("image"))
            {
                ((SenderViewHolder)holder).senderTime.setText(messageModel.getTimestamp());
                ((SenderViewHolder)holder).senderMsg.setText("");
                ((SenderViewHolder)holder).senderPic.setVisibility(View.VISIBLE);
                Picasso.get().load(messageModel.getMessage().toString()).into(((SenderViewHolder)holder).senderPic);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String [] chats = {"Delete Image"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Image Options")
                                .setItems(chats, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(chats[which].equals("Delete Image"))
                                        {
                                            // Deleting from Sender Room
                                            final String senderRoom = senderID + receiverID;
                                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Chats").child(senderRoom).child(messageModels.get(position).getMessageID());
                                            dbRef.removeValue();
                                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Image Messages").child(messageModels.get(position).getMessageID() + ".jpg");
                                            storageReference.delete();

                                            // Deleting from Receiver Room
                                            final String receiverRoom = receiverID + senderID;
                                            dbRef = FirebaseDatabase.getInstance().getReference("Chats").child(receiverRoom).child(messageModels.get(position).getMessageID());
                                            dbRef.removeValue();
                                            Toast.makeText(context, "Deleted succesfully", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(context, "deletion failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        builder.show();
                    }
                });
            }
        }
        else
        {
            if(messageModel.getType().equals("text"))
            {
                ((ReceiverViewHolder)holder).receiverMsg.setText(messageModel.getMessage());
                ((ReceiverViewHolder)holder).receiverTime.setText(messageModel.getTimestamp());
            }
            else if(messageModel.getType().equals("image")) {
                ((ReceiverViewHolder) holder).receiverTime.setText(messageModel.getTimestamp());
                ((ReceiverViewHolder) holder).receiverMsg.setText("");
                ((ReceiverViewHolder) holder).receiverPic.setVisibility(View.VISIBLE);
                Picasso.get().load(messageModel.getMessage().toString()).into(((ReceiverViewHolder)holder).receiverPic);
            }
        }
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    // For reciever
    public class ReceiverViewHolder extends RecyclerView.ViewHolder{
        TextView receiverMsg, receiverTime;
        ImageView receiverPic;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMsg = itemView.findViewById(R.id.receiverText);
            receiverTime = itemView.findViewById(R.id.receiverTime);
            receiverPic = itemView.findViewById(R.id.receiverPic);
        }
    }
    // For Sender
    public class SenderViewHolder extends RecyclerView.ViewHolder{
        TextView senderMsg, senderTime;
        ImageView senderPic;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMsg = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);
            senderPic = itemView.findViewById(R.id.senderPic);
        }
    }
}
