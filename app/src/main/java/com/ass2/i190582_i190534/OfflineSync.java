package com.ass2.i190582_i190534;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class OfflineSync extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
