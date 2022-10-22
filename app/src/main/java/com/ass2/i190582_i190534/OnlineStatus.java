package com.ass2.i190582_i190534;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.apphosting.datastore.testing.DatastoreTestTrace;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class OnlineStatus {
    public static void updateUserStatus(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        DatabaseReference statusRef = db.getReference().child("Users").child(mAuth.getUid()).child("status");
        DatabaseReference lastSeenRef = db.getReference().child("Users").child(mAuth.getUid()).child("last_seen");

        //if(mAuth.getCurrentUser() == null )
        //{
        //    statusRef.setValue(true);
        //    statusRef.onDisconnect().setValue(false);
        //    lastSeenRef.onDisconnect().setValue(Calendar.getInstance().getTime());
        //    return;
        //}
        statusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String status = snapshot.getValue(String.class);
                if(status.equals("false")){
                    statusRef.setValue("true");
                    statusRef.onDisconnect().setValue("false");
                    lastSeenRef.onDisconnect().setValue(Calendar.getInstance().getTime());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error: " + error);
            }
        });
    }
}

