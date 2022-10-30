package com.ass2.i190582_i190534;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
//import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.constraintlayout.core.motion.utils.Utils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class PlaySong extends AppCompatActivity {
    ImageView playSong, nextSong, previousSong, comment, like_song, listen_later;
    int number = 0;
    private SeekBar seekBar;
    MediaPlayer mediaPlayer;
    private Handler mHandler = new Handler();
    DataSnapshot dataSnapshot;
    String url, title;
    FirebaseDatabase db;
    TextView song_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);

        seekBar = (SeekBar) findViewById(R.id.seekbar);
        playSong = findViewById(R.id.playsong);
        nextSong = findViewById(R.id.next_song);
        previousSong = findViewById(R.id.previous_song);
        comment = findViewById(R.id.comment);
        like_song = findViewById(R.id.like_song);
        listen_later = findViewById(R.id.listen_later1);
        db = FirebaseDatabase.getInstance();
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        song_title = findViewById(R.id.song_title);
        song_title.setText(title);



        // Snapshot
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Songs").keepSynced(true);
        reference.child("Songs").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        dataSnapshot = task.getResult();
                    }
                }
            }
        });

        playSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(playSong.getBackground().getConstantState() == getDrawable(R.drawable.pause_page14).getConstantState()) {
                    playSong.setBackgroundResource(R.drawable.songname_pause_screen5);
                    mediaPlayer.pause();
                }
                else{
                    playSong.setBackgroundResource(R.drawable.pause_page14);
                    if (number == 0) {
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
                        number = 1;
                    }
                    else {
                        mediaPlayer.start();
                    }
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
        previousSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        nextSong.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
//                boolean flag = false;
//                Song first = null;
//                Song songs = null;
//                boolean one = true;
//                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
//                    songs = userSnapshot.getValue(Song.class);
//                    if(one == true ){
//                        first = songs;
//                        one = false;
//                    }
//
//                    if(songs.title.equals(title)){
//                        flag = true;
//                        one = true;
//                    }
//                    if(flag == true){
//                        url = songs.url;
//                        mediaPlayer.stop();
//                        number = 0;
//                        title = songs.title;
//                        mediaPlayer.seekTo(0);
//                        playSong.setBackgroundResource(R.drawable.songname_pause_screen5);
//                        one = false;
//                        break;
//                    }
//                }
//                if(one  == true){
//                    url = first.url;
//                    mediaPlayer.stop();
//                    number = 0;
//                    title = songs.title;
//                    mediaPlayer.seekTo(0);
//                    playSong.setBackgroundResource(R.drawable.songname_pause_screen5);
//
//                }
//            }

                //   });
            }
        });
    comment.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), SongComments.class);
            intent.putExtra("song", title);
            startActivity(intent);
        }
    });
    like_song.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(PlaySong.this, "Song Liked!", Toast.LENGTH_SHORT).show();
            db.getReference().child("Liked Songs").child(FirebaseAuth.getInstance().getUid()).child(title).child("title").setValue(title);
            db.getReference().child("Liked Songs").child(FirebaseAuth.getInstance().getUid()).child(title).child("url").setValue(url);

        }
    });
        listen_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlaySong.this, "Added to Listen Later!", Toast.LENGTH_SHORT).show();
                db.getReference().child("Listen Later").child(FirebaseAuth.getInstance().getUid()).child(title).child("title").setValue(title);
                db.getReference().child("Listen Later").child(FirebaseAuth.getInstance().getUid()).child(title).child("url").setValue(url);

            }
        });
    }

}
