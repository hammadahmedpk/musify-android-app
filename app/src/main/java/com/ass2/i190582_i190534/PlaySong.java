package com.ass2.i190582_i190534;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;


public class PlaySong extends AppCompatActivity {
    ImageView playSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        playSong=findViewById(R.id.playsong);
        playSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://firebasestorage.googleapis.com/v0/b/musify-android-app.appspot.com/o/audios%2FgmsE7jAfGKfh3KrCFHf2EhZhho22?alt=media&token=39e7e382-0bc4-42ee-8776-5d495493c26a"; // your URL here
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioAttributes(
                        new AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .build()
                );
                try {
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepare(); // might take long! (for buffering, etc)
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}

