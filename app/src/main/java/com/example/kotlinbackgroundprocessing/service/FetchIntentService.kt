package com.example.kotlinbackgroundprocessing.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.kotlinbackgroundprocessing.app.PhotosUtils

class FetchIntentService : IntentService("FetchIntentService") {

    companion object {
        private const val TAG = "FetchIntentService"
        private const val ACTION_FETCH = "ACTION_FETCH"
        const val FETCH_COMPLETE = "FETCH_COMPLETE"

        fun startActionFetch(context: Context) {
            val intent = Intent(context, FetchIntentService::class.java).apply {
                action = ACTION_FETCH
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
            ACTION_FETCH -> handleActionFetch()
        }
    }

    private fun handleActionFetch() {
        try {
            Log.i(TAG, "Starting fetch JSON")
            PhotosUtils.fetchJsonString()
            Log.i(TAG, "Ending fetch JSON")
        } catch (e: InterruptedException) {
            Log.e(TAG, "Error fetch JSON: ${e.message}")
        }

        Log.i(TAG, "Sending broadcast")
        broadcastFetchComplete()
    }

    private fun broadcastFetchComplete() {
        val intent = Intent(FETCH_COMPLETE)
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
    }
}