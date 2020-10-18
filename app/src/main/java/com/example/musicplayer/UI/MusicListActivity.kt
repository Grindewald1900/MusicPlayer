package com.example.musicplayer.UI

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.data.MusicInfo
import com.example.musicplayer.tools.MusicAdapter
import com.example.musicplayer.tools.MusicResolver
import com.yanzhenjie.permission.Action
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission


class MusicListActivity : AppCompatActivity(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var refreshBtn: Button
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var musicList: ArrayList<MusicInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
    }

    fun initView(){
        var mDivider: DividerItemDecoration = DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        viewManager = LinearLayoutManager(this)
        viewAdapter = MusicAdapter(musicList)
        recyclerView = findViewById<RecyclerView>(R.id.music_recycler_view)
        recyclerView.layoutManager = viewManager
        recyclerView.adapter = viewAdapter
    }

    fun initData(){
        AndPermission.with(this)
            .runtime()
            .permission(Permission.Group.STORAGE)
            .onGranted(Action { permissions: List<String?>? ->
                musicList = MusicResolver.getMusicInfo(this)
                Log.i("musiclist", "Count: " + musicList.size.toString())
                Log.i("musiclist", musicList.toString())
                initView()

            })
            .onDenied(Action { permissions: List<String?>? -> })
            .start()
//        refreshBtn = findViewById(R.id.music_refresh_btn)
//        refreshBtn.setOnClickListener {
//            musicList = MusicResolver.getMusicInfo(this)
//            Log.i("musiclist", "Count: " + musicList.size.toString())
//
//            Log.i("musiclist", musicList.toString())
//        }

    }

//    override fun onRecyclerViewItemClick(view: View, position: Int){
//        Log.i("main","position:"+position.toString())
//    }
}