package com.example.kotlinbackgroundprocessing.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.kotlinbackgroundprocessing.R
import com.example.kotlinbackgroundprocessing.app.App
import com.example.kotlinbackgroundprocessing.app.SongUtils
import com.example.kotlinbackgroundprocessing.ui.main.MainActivity

class SongService : Service() {

    companion object {
        private const val CHANNEL_NAME = "Media playback"
        private const val CHANNEL_ID = "MEDIA_PLAYBACK"
        private const val NOTIFICATION_TITLE = "Playing song"
    }

    private lateinit var mediaPlayer: MediaPlayer

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        startForeground(1000, createNotification())
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

    private fun createNotification(): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }

        val notificationIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val intent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentIntent(intent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .setContentTitle(NOTIFICATION_TITLE)
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val name = CHANNEL_NAME
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            setShowBadge(false)
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }

        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        notificationManager?.createNotificationChannel(channel)
    }
}