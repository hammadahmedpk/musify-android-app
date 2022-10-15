package com.ass2.i190582_i190534;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CreateProfile extends AppCompatActivity {

    ImageView dp, dpRound;
    Uri dpp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        dp = findViewById(R.id.dp);
        dpRound = findViewById(R.id.dpRound);
        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(i, "Choose your DP"),
                        100);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK){
            dpp = data.getData();
            dpRound.setImageURI(dpp);
            dpRound.setBackgroundResource(R.drawable.round_circle);
            dpRound.setMinimumWidth(200);
            dpRound.setMinimumHeight(200);
        }
    }
}