package com.ass2.i190582_i190534;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class MessageDialogueBox extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String [] chats = {"Edit Message", "Delete Message"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Message Options")
                .setItems(chats, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(chats[which].equals("Edit")){
                            Intent intent = new Intent(getActivity(), NewChat.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(getActivity(), ListenLater.class);
                            startActivity(intent);
                        }
                    }
                });
        return builder.create();
    }
}
