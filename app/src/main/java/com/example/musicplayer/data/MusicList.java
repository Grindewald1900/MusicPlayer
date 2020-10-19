package com.example.musicplayer.data;

import java.util.ArrayList;

public class MusicList {
    private static MusicList musicList;
    private static ArrayList<MusicInfo> list;

    private MusicList(){ }

    public static MusicList getInstance(){
        if(null == musicList){
            musicList = new MusicList();
        }
        return musicList;
    }

    public void setList(ArrayList<MusicInfo> mList){
        list = mList;
    }

    public ArrayList<MusicInfo> getList(){
        return list;
    }

    public MusicInfo getMusic(int id){
        return list.get(id);
    }
}
