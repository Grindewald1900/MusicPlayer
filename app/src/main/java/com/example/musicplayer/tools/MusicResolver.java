package com.example.musicplayer.tools;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.example.musicplayer.data.MusicInfo;

import java.util.ArrayList;

public class MusicResolver {
    private static final Uri URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private static final String[] MUSIC_PROJECTION = new String[] {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
//            MediaStore.Audio.Media.ALBUM,
//            MediaStore.Audio.Media.ARTIST,
//            MediaStore.Audio.Media.DURATION,
//            MediaStore.Audio.Media.SIZE
    };
    private static final String SELECTION = "mime_type in ('audio/mpeg','audio/x-ms-wma') and bucket_display_name <> 'audio' and is_music > 0 ";
    private static final String SORT_ORDER = MediaStore.Audio.Media.DATA;

    public static ArrayList<MusicInfo> getMusicInfo(Context context) {
        ArrayList<MusicInfo> musicInfos = new ArrayList<>();
        ContentResolver resolver = context.getApplicationContext().getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.AudioColumns.IS_MUSIC);
//        Cursor cursor = resolver.query(URI, MUSIC_PROJECTION, null, null, null);
        if (cursor != null) {
            int c = cursor.getCount();
            while (cursor.moveToNext()) {
                MusicInfo musicInfo = new MusicInfo();
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                String data = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

                musicInfo.setId(id);
                if (!TextUtils.isEmpty(data)) {
                    musicInfo.setData(data);
                }
                if (!TextUtils.isEmpty(title)) {
                    musicInfo.setTitle(title);
                }
                if (!TextUtils.isEmpty(album)) {
                    musicInfo.setAlbum(album);
                }
                if (!TextUtils.isEmpty(artist)) {
                    musicInfo.setArtist(artist);
                }
                if (duration != 0) {
                    musicInfo.setDuration(duration);
                }

                musicInfos.add(musicInfo);
            }
            cursor.close();
        }
        return musicInfos;
    }
}
