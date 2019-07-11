package com.example.kotlinbackgroundprocessing.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import com.example.kotlinbackgroundprocessing.app.App
import com.example.kotlinbackgroundprocessing.app.SongUtils

class SongService : Service() {
    private lateinit var mediaPlayer: MediaPlayer

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer = MediaPlayer.create(this, Uri.fromFile(SongUtils.songFile()))
        mediaPlayer.isLooping = true
        mediaPlayer.start()
        App.isPlayingSong = true

        return START_STICKY
    }

    override fun onDestroy() {
        mediaPlayer.stop()
        App.isPlayingSong = false
        super.onDestroy()
    }
}
