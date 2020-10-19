package com.example.musicplayer.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

import com.example.musicplayer.R;
import com.example.musicplayer.UI.MusicListActivity;

import static android.app.PendingIntent.getActivity;

public class MusicPlayerService extends Service {
    private static final String TAG = MusicPlayerService.class.getSimpleName();

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(TAG, "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d(TAG, "onStartCommand()");
//        Notification.Builder builder = new Notification.Builder
//        (this.getApplicationContext()); //获取一个Notification构造器
//        Intent nfIntent = new Intent(this, MusicListActivity.class);
//
//        builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0)).
//                setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.app_icon)).
//                setContentTitle("下拉列表中的Title").
//                setSmallIcon(R.mipmap.ic_launcher).
//                setContentText("要显示的内容").
//                setWhen(System.currentTimeMillis());
//        Notification notification = builder.build(); // 获取构建好的Notification
//        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
//        startForeground(110, notification);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent){
        Log.d(TAG, "onBind()");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        stopForeground(true);
        super.onDestroy();
    }
}
