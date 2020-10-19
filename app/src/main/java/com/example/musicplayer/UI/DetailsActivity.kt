package com.example.musicplayer.UI

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.musicplayer.R
import com.example.musicplayer.data.MusicInfo
import com.example.musicplayer.data.MusicList
import com.example.musicplayer.service.MusicService
import com.example.musicplayer.tools.CreateNotification
import kotlinx.android.synthetic.main.activity_details.*
import kotlin.properties.Delegates


class DetailsActivity : AppCompatActivity() {
    private lateinit var notificationManager: NotificationManager
    private lateinit var music: MusicInfo
    private var isPlay: Boolean = false
    private lateinit var mediaPlayer: MediaPlayer
    private var musicId by Delegates.notNull<Int>()
    private var isPrepared: Boolean = false
    private var keepUpdate: Boolean = true
    private var currentPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initData()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (mediaPlayer != null){
            mediaPlayer.reset()
            mediaPlayer.reset()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationManager.cancelAll()
        }
        unregisterReceiver(broadcastReceiver)
    }

    private fun initData(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel()
            registerReceiver(broadcastReceiver, IntentFilter("MUSIC"))
            startService(Intent(baseContext, MusicService::class.java))
        }

        mediaPlayer = MediaPlayer()
        musicId = intent.getIntExtra("Id", 1)
        Glide.with(this).load(R.drawable.music).placeholder(R.drawable.music).into(detail_img)
        detail_last.setOnClickListener {
            playLast()
        }
        detail_next.setOnClickListener {
            playNext()
        }
        detail_pause.setOnClickListener {
            if(isPlay){
                if (mediaPlayer.isPlaying) pauseMusic()
            }else{
                continueMusic()
            }
        }
        detail_seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var duration: Int = mediaPlayer.duration
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null && isPrepared) {
                    mediaPlayer.seekTo(seekBar.progress)
                }
            }
        })
        initView()
        updateSeekBar()
    }

    private fun initView(){
        music = MusicList.getInstance().getMusic(musicId)
        detail_album.text = music.album
        detail_artist.text = music.artist
        playMusic()
        CreateNotification.createNotification(this, music, R.drawable.icon_start,1, 309)
    }

    private fun playMusic(){
        detail_pause.setImageResource(R.drawable.icon_pause)
        if (null == mediaPlayer){
            mediaPlayer = MediaPlayer()
        }
        mediaPlayer.setDataSource(music.data)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
            isPlay = true
            isPrepared = true
        }
    }

    private fun continueMusic(){
        detail_pause.setImageResource(R.drawable.icon_pause)
        mediaPlayer.start()
        isPlay = true
        CreateNotification.createNotification(this, music, R.drawable.icon_pause_24, musicId, MusicList.getInstance().list.size -1)
    }

    private fun pauseMusic(){
        detail_pause.setImageResource(R.drawable.icon_start)
        mediaPlayer.pause()
        isPlay = false
        CreateNotification.createNotification(this, music, R.drawable.icon_start_24, musicId, MusicList.getInstance().list.size -1)

    }

    private fun playLast(){
        musicId --
        mediaPlayer.reset()
        isPrepared = false
        initView()
        CreateNotification.createNotification(this, music, R.drawable.icon_pause_24, musicId, MusicList.getInstance().list.size -1)
    }

    private fun playNext(){
        musicId ++
        mediaPlayer.reset()
        isPrepared = false
        initView()
        CreateNotification.createNotification(this, music, R.drawable.icon_pause_24, musicId, MusicList.getInstance().list.size -1)

    }


    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val data: Bundle = msg.getData()
            val duration = data.getInt("duration")
            val currentPosition = data.getInt("currentPosition")
            detail_seekbar.setMax(duration)
            detail_seekbar.setProgress(currentPosition)
        }
    }

    private fun updateSeekBar(){
        Thread{
            while (keepUpdate){
                try {
                    Thread.sleep(1000);
                }catch (e: InterruptedException){
                    e.printStackTrace()
                }
                currentPosition = mediaPlayer.currentPosition
                val message = Message.obtain()
                val bundle = Bundle()
                bundle.putInt("duration", mediaPlayer.duration)
                bundle.putInt("currentPosition", currentPosition)
                message.data = bundle
                handler.sendMessage(message)
            }
        }.start()
    }

    var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.extras!!.getString("actionname")
            when (action) {
                CreateNotification.ACTION_LAST -> {
                    playLast()
                }
                CreateNotification.ACTION_PLAY -> {
                    if(isPlay){
                        pauseMusic()
                    }else{
                        continueMusic()
                    }
                }
                CreateNotification.ACTION_NEXT -> {
                    playNext()
                }
            }
        }
    }

    private fun createChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var channel = NotificationChannel("channel", "Music", NotificationManager.IMPORTANCE_HIGH)
            notificationManager = getSystemService(NotificationManager::class.java)
            if(notificationManager != null){
                notificationManager.createNotificationChannel(channel)
            }
        }
    }


}