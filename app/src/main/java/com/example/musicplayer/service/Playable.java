package com.example.musicplayer.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.musicplayer.tools.CreateNotification;

public interface Playable {
    void onMusicLast();
    void onMusicPlay();
    void onMusicPause();
    void onMusicNext();

}
