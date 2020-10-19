package com.example.musicplayer.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

import com.example.musicplayer.R;
import com.example.musicplayer.UI.MusicListActivity;

import static android.app.PendingIntent.getActivity;

public class MusicPlayerService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        context.sendBroadcast(new Intent("MUSIC").putExtra("actionname", intent.getAction()));
    }
}
