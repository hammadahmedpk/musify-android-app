package com.ass2.i190582_i190534;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class ChatDialogueBox extends AppCompatDialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String [] chats = {"Chat", "Group Chat"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Create New")
                .setItems(chats, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(chats[which].equals("Chat")){
                            Intent intent = new Intent(getActivity(), MusicLibrary.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(getActivity(), ListenLater.class);
                            startActivity(intent);
                        }
                        // The 'which' argument contains the index position
                        // of the selected item
                    }
                });
        return builder.create();
    }
}
