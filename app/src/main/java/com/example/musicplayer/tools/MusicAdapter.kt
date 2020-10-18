package com.example.musicplayer.tools

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.data.MusicInfo
import com.example.musicplayer.data.MusicItem
import kotlinx.android.synthetic.main.activity_main.view.*

class MusicAdapter(private val musicList: List<MusicInfo>) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    class MusicViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val musicCover: ImageView = view.findViewById(R.id.music_cover)
        val musicTitle: TextView = view.findViewById(R.id.music_title)
        val musicAuthor: TextView = view.findViewById(R.id.music_author)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val musicView = LayoutInflater.from(parent.context).inflate(R.layout.music_item, parent,false)
        return MusicViewHolder(musicView)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val music = musicList[position]
        holder.musicTitle.text = music.album
        holder.musicAuthor.text = music.artist
    }

    override fun getItemCount() = musicList.size

}