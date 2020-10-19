package com.example.musicplayer.UI

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.data.MusicInfo
import com.example.musicplayer.data.MusicList
import com.example.musicplayer.tools.CreateNotification
import com.example.musicplayer.tools.MusicAdapter
import com.example.musicplayer.tools.MusicResolver
import com.yanzhenjie.permission.Action
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission


class MusicListActivity : AppCompatActivity(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var refreshBtn: Button
    private lateinit var viewAdapter: MusicAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var musicList: ArrayList<MusicInfo>
    private lateinit var notificationManager: NotificationManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
    }

    private fun initView(){
        var mDivider: DividerItemDecoration = DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        viewManager = LinearLayoutManager(this)
        recyclerView = findViewById<RecyclerView>(R.id.music_recycler_view)
        recyclerView.layoutManager = viewManager
        recyclerView.adapter = MusicAdapter(this.musicList) { musicInfo: MusicInfo -> musicItemClicked(musicInfo) }
    }

    private fun initData(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel()
        }
        AndPermission.with(this)
            .runtime()
            .permission(Permission.Group.STORAGE)
            .onGranted(Action { permissions: List<String?>? ->
                musicList = MusicResolver.getMusicInfo(this)
                MusicList.getInstance().setList(musicList)
                Log.i("musiclist", "Count: " + musicList.size.toString())
                initView()

            })
            .onDenied(Action { permissions: List<String?>? -> })
            .start()

    }

    private fun musicItemClicked(musicInfo: MusicInfo){
        val intent = Intent(this, DetailsActivity::class.java)
//        intent.putStringArrayListExtra("musicInfo", musicArray)
        intent.putExtra("Id", musicList.indexOf(musicInfo))
        CreateNotification.createNotification(this, musicInfo, R.drawable.icon_start,1, musicList.size - 1)
        startActivity(intent)
    }

    private fun createChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var channel: NotificationChannel  = NotificationChannel("channel0", "Music", NotificationManager.IMPORTANCE_HIGH)
            notificationManager = getSystemService(NotificationManager::class.java)
            if(notificationManager != null){
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}