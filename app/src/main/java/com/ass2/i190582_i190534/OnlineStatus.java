package com.ass2.i190582_i190534;

import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.apphosting.datastore.testing.DatastoreTestTrace;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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

                    //Calendar.getInstance().getTime();
                    long UTC_TIMEZONE=Calendar.getInstance().getTimeInMillis();
                    String OUTPUT_DATE_FORMATE="dd-MM-yyyy - hh:mm a";
                    //String time = getDate(Calendar.getInstance().getTimeInMillis());

                    Date dateAndTime = Calendar.getInstance().getTime();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy", Locale.getDefault());
                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                    String time = timeFormat.format(dateAndTime);
                    String date = dateFormat.format(dateAndTime);

                    String dateTime = date + " " + time;

                    //Calendar calendar = Calendar.getInstance();
                    //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy hh:mm a");
                    //String dateTime = simpleDateFormat.format(calendar.getTime());
                    lastSeenRef.onDisconnect().setValue(dateTime);
                    //lastSeenRef.onDisconnect().setValue(getDateFromUTCTimestamp(new Date().getTime(),OUTPUT_DATE_FORMATE));
                    //lastSeenRef.onDisconnect().setValue(getDateFromUTCTimestamp(UTC_TIMEZONE,OUTPUT_DATE_FORMATE));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error: " + error);
            }
        });
    }

    public static String getDateFromUTCTimestamp(long mTimestamp, String mDateFormate) {
        String date = null;
        try {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            cal.setTimeInMillis(mTimestamp * 1000L);
            date = DateFormat.format(mDateFormate, cal.getTimeInMillis()).toString();

            SimpleDateFormat formatter = new SimpleDateFormat(mDateFormate);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(date);

            SimpleDateFormat dateFormatter = new SimpleDateFormat(mDateFormate);
            dateFormatter.setTimeZone(TimeZone.getDefault());
            date = dateFormatter.format(value);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    private static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }
}



