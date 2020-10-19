package com.example.musicplayer.tools;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.session.MediaSession;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.musicplayer.R;
import com.example.musicplayer.data.MusicInfo;

public class CreateNotification {
    public static Notification notification;

    public static void createNotification(Context context, MusicInfo musicInfo, int playBtn, int pos, int size){
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.app_icon);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            MediaSession mediaSessionCompat = new MediaSession(context, "tag");

            notification = new NotificationCompat.Builder(context, "channel0").setSmallIcon(R.drawable.app_icon).
                    setContentTitle(musicInfo.getAlbum()).setContentText(musicInfo.getArtist()).setLargeIcon(icon).
                    setShowWhen(false).setPriority(NotificationCompat.PRIORITY_HIGH).build();

            notificationManagerCompat.notify(1,notification);


        }

    }

}
