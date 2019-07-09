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
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.kotlinbackgroundprocessing.R
import com.example.kotlinbackgroundprocessing.app.App
import com.example.kotlinbackgroundprocessing.app.Constants
import com.example.kotlinbackgroundprocessing.app.SongUtils
import com.example.kotlinbackgroundprocessing.service.DownloadIntentService
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
                play_button.isEnabled = true
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
    }

    override fun onResume() {
        super.onResume()

        if (SongUtils.songFile().exists()) {
            play_button.isEnabled = true
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
}