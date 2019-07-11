package com.example.kotlinbackgroundprocessing.ui.song

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.kotlinbackgroundprocessing.R
import com.example.kotlinbackgroundprocessing.app.App
import com.example.kotlinbackgroundprocessing.app.Constants
import com.example.kotlinbackgroundprocessing.app.SongUtils
import com.example.kotlinbackgroundprocessing.service.DownloadIntentService
import com.example.kotlinbackgroundprocessing.service.SongService
import kotlinx.android.synthetic.main.fragment_song.*

class SongFragment : Fragment() {

    companion object {
        private const val TAG = "SongFragment"

        fun newInstance() = SongFragment()
    }

    private val localBroadcastManager = LocalBroadcastManager.getInstance(App.getAppContext())

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val param = intent?.getStringExtra(DownloadIntentService.DOWNLOAD_COMPLETE_KEY)
            Log.i(TAG, "Received broadcast for $param")

            if (SongUtils.songFile().exists()) {
                enablePlayButton()
                download_button.isEnabled = false
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        download_button.setOnClickListener {
            DownloadIntentService.startActionDownload(view.context, Constants.SONG_URL)
        }

        play_button.setOnClickListener {
            startPlaying()
        }

        stop_button.setOnClickListener {
            stopPlaying()
        }
    }

    override fun onResume() {
        super.onResume()

        if (App.isPlayingSong) {
            enableStopButton()
        } else {
            enablePlayButton()
        }

        if (!SongUtils.songFile().exists()) {
            disableMediaButton()
            download_button.isEnabled = true
        }
    }

    override fun onStart() {
        super.onStart()
        localBroadcastManager.registerReceiver(receiver, IntentFilter(DownloadIntentService.DOWNLOAD_COMPLETE))
    }

    override fun onStop() {
        super.onStop()
        localBroadcastManager.unregisterReceiver(receiver)
    }

    private fun startPlaying() {
        context?.let {
            ContextCompat.startForegroundService(it, Intent(it, SongService::class.java))
        }
        enableStopButton()
    }

    private fun stopPlaying() {
        activity?.stopService(Intent(context, SongService::class.java))
        enablePlayButton()
    }

    private fun enablePlayButton() {
        play_button.isEnabled = true
        stop_button.isEnabled = false
    }

    private fun enableStopButton() {
        play_button.isEnabled = false
        stop_button.isEnabled = true
    }

    private fun disableMediaButton() {
        play_button.isEnabled = false
        stop_button.isEnabled = false
    }
}