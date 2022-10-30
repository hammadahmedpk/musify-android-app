package com.ass2.i190582_i190534;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


    public class CreateNotification {

        public static final String CHANNEL_ID = "channel1";

        public static final String ACTION_PREVIUOS = "actionprevious";
        public static final String ACTION_PLAY = "actionplay";
        public static final String ACTION_NEXT = "actionnext";

        public static Notification notification;

        public static void createNotification(Context context, String title){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
                MediaSessionCompat mediaSessionCompat = new MediaSessionCompat( context, "tag");
                Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.despacito);

                //create notification
                notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.music_uploaded_icon)
                        .setContentTitle(title)
                        .setContentText("Song")
                        .setLargeIcon(icon)
                        .setOnlyAlertOnce(true)//show notification for only first time
                        .setShowWhen(false)
                        .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                                .setShowActionsInCompactView(0, 1, 2)
                                .setMediaSession(mediaSessionCompat.getSessionToken()))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .build();

                notificationManagerCompat.notify(1, notification);

            }
        }
    }

