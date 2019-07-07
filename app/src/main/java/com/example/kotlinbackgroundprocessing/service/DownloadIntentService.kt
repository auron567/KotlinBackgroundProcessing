package com.example.kotlinbackgroundprocessing.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.kotlinbackgroundprocessing.app.SongUtils

class DownloadIntentService : IntentService("DownloadIntentService") {

    companion object {
        private const val TAG = "DownloadIntentService"
        private const val ACTION_DOWNLOAD = "ACTION_DOWNLOAD"
        private const val EXTRA_URL = "EXTRA_URL"

        fun startActionDownload(context: Context, param: String) {
            val intent = Intent(context, DownloadIntentService::class.java).apply {
                action = ACTION_DOWNLOAD
                putExtra(EXTRA_URL, param)
            }

            context.startService(intent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Creating service")
    }

    override fun onDestroy() {
        Log.i(TAG, "Destroying service")
        super.onDestroy()
    }

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_DOWNLOAD -> {
                handleActionDownload(intent.getStringExtra(EXTRA_URL))
            }
        }
    }

    private fun handleActionDownload(param: String) {
        Log.i(TAG, "Starting download for $param")
        SongUtils.download(param)
        Log.i(TAG, "Ending download for $param")
    }
}