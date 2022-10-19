package com.ass2.i190582_i190534;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
//import android.support.v4.media.session.MediaSessionCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
//import androidx.constraintlayout.core.motion.utils.Utils;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.File;
import java.io.IOException;
import java.security.acl.LastOwnerException;

public class PlaySong extends AppCompatActivity {
    ImageView playSong;
    private SeekBar seekBar;
    MediaPlayer mediaPlayer;
    private Handler mHandler = new Handler();
    NotificationManager notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        playSong = findViewById(R.id.playsong);
        playSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CreateNotification.createNotification(PlaySong.this,"Usw","No", 1, 1);


                //  notificationChannel();//////////////////////////////////////////
                String url = "https://firebasestorage.googleapis.com/v0/b/musify-android-app.appspot.com/o/audios%2FgmsE7jAfGKfh3KrCFHf2EhZhho22%2Fuploaded_songs%2Fnew?alt=media&token=3355e935-34cd-4340-852a-c367cf0c1b90"; // your URL here
                mediaPlayer = new MediaPlayer();
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
                    seekBar.setMax(mediaPlayer.getDuration() / 1000);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        PlaySong.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                }
                mHandler.postDelayed(this, 1000);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
//    public void createChannel(){
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
//            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID_1, "Uswa", NotificationManager.IMPORTANCE_LOW);
//            notificationManager = getSystemService(NotificationManager.class);
//            if(notificationManager != null){
//                notificationManager.createNotificationChannel(channel);
//            }
//        }
//    }
}