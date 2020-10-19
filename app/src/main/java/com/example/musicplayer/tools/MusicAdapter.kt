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
import kotlinx.android.synthetic.main.music_item.view.*

class MusicAdapter(private val musicList: List<MusicInfo>, val clickListener: (MusicInfo) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val musicView = LayoutInflater.from(parent.context).inflate(R.layout.music_item, parent,false)
        return MusicViewHolder(musicView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MusicViewHolder).bind(musicList[position], clickListener)
    }

    override fun getItemCount() = musicList.size

    class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(music: MusicInfo, clickListener: (MusicInfo) -> Unit){
            itemView.music_title.text = music.album
            itemView.music_author.text = music.artist
            itemView.setOnClickListener{clickListener(music)}
        }
    }


}