package com.example.musicplayer.tools;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import android.media.session.MediaSession;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.musicplayer.R;
import com.example.musicplayer.data.MusicInfo;
import com.example.musicplayer.service.MusicPlayerReceiver;

public class CreateNotification {
    public static Notification notification;
    public static final String CHANNEL = "channel";
    public static final String ACTION_LAST = "action_last";
    public static final String ACTION_PLAY = "action_play";
    public static final String ACTION_NEXT = "action_next";

    public static void createNotification(Context context, MusicInfo musicInfo, int playBtn, int pos, int size){
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_music);
        PendingIntent pendingIntentLast;
        int d_last;
        if(pos == 0){
            pendingIntentLast = null;
            d_last = 0;
        }else {
            Intent intentLast = new Intent(context, MusicPlayerReceiver.class).setAction(ACTION_LAST);
            pendingIntentLast = PendingIntent.getBroadcast(context,0, intentLast, PendingIntent.FLAG_UPDATE_CURRENT);
            d_last = R.drawable.icon_last_24;
        }

        Intent intentPlay = new Intent(context, MusicPlayerReceiver.class).setAction(ACTION_PLAY);
        PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context,0, intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent pendingIntentNext;
        int d_next;
        if(pos == size){
            pendingIntentNext = null;
            d_next = 0;
        }else {
            Intent intentNext = new Intent(context, MusicPlayerReceiver.class).setAction(ACTION_NEXT);
            pendingIntentNext = PendingIntent.getBroadcast(context,0, intentNext, PendingIntent.FLAG_UPDATE_CURRENT);
            d_next = R.drawable.icon_next_24;
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");

            notification = new NotificationCompat.Builder(context, CHANNEL).setSmallIcon(R.drawable.icon_music).
                    setContentTitle(musicInfo.getAlbum()).setContentText(musicInfo.getArtist()).setLargeIcon(icon).
                    setShowWhen(false).
                    addAction(d_last, "last", pendingIntentLast).
                    addAction(playBtn, "play", pendingIntentPlay).
                    addAction(d_next,"next", pendingIntentNext).
                        setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0,1,2).
                        setMediaSession(mediaSessionCompat.getSessionToken())).
                    setPriority(NotificationCompat.PRIORITY_HIGH).build();

            notificationManagerCompat.notify(1,notification);


        }

    }

}
