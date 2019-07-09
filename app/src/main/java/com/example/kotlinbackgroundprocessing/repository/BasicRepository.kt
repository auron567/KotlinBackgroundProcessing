package com.example.kotlinbackgroundprocessing.repository

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.*
import com.example.kotlinbackgroundprocessing.app.App
import com.example.kotlinbackgroundprocessing.app.PhotosUtils
import com.example.kotlinbackgroundprocessing.service.FetchIntentService
import com.example.kotlinbackgroundprocessing.service.LogWorker
import com.example.kotlinbackgroundprocessing.service.PhotosWorker
import java.util.concurrent.TimeUnit

object BasicRepository : Repository {
    private val photosLiveData = MutableLiveData<List<String>>()
    private val bannerLiveData = MutableLiveData<String>()
    private val localBroadcastManager = LocalBroadcastManager.getInstance(App.getAppContext())

    private const val PHOTOS_WORKER_TAG = "PHOTOS_WORKER_TAG"
    private const val LOG_WORKER_TAG = "LOG_WORKER_TAG"

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            FetchBannerAsyncTask { banner ->
                bannerLiveData.value = banner
            }.execute()

            FetchPhotosAsyncTask { photos ->
                photosLiveData.value = photos
            }.execute()
        }
    }

    init {
        schedulePhotosWorker()
        scheduleLogWorker()
    }

    override fun getPhotos(): LiveData<List<String>> {
        FetchPhotosAsyncTask { photos ->
            photosLiveData.value = photos
        }.execute()

        return photosLiveData
    }

    override fun getBanner(): LiveData<String> {
        FetchBannerAsyncTask { banner ->
            bannerLiveData.value = banner
        }.execute()

        return bannerLiveData
    }

    override fun register() {
        localBroadcastManager.registerReceiver(receiver, IntentFilter(FetchIntentService.FETCH_COMPLETE))
    }

    override fun unregister() {
        localBroadcastManager.unregisterReceiver(receiver)
    }

    private fun schedulePhotosWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequestBuilder<PhotosWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .addTag(PHOTOS_WORKER_TAG)
            .build()

        with(WorkManager.getInstance()) {
            cancelAllWorkByTag(PHOTOS_WORKER_TAG)
            enqueue(request)
        }
    }

    private fun scheduleLogWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<LogWorker>()
            .setConstraints(constraints)
            .addTag(LOG_WORKER_TAG)
            .build()

        with(WorkManager.getInstance()) {
            cancelAllWorkByTag(LOG_WORKER_TAG)
            enqueue(request)
        }
    }

    private class FetchPhotosAsyncTask(val callback: (List<String>) -> Unit)
        : AsyncTask<Void, Void, List<String>>() {

        override fun doInBackground(vararg params: Void?): List<String>? {
            val photosString = PhotosUtils.photoJsonString()
            return PhotosUtils.photoUrlsFromJsonString(photosString ?: "")
        }

        override fun onPostExecute(result: List<String>?) {
            if (result != null) {
                callback(result)
            }
        }
    }

    private class FetchBannerAsyncTask(val callback: (String) -> Unit)
        : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg params: Void?): String? {
            val photosString = PhotosUtils.photoJsonString()
            return PhotosUtils.bannerUrlFromJsonString(photosString ?: "")
        }

        override fun onPostExecute(result: String?) {
            if (result != null) {
                callback(result)
            }
        }
    }
}