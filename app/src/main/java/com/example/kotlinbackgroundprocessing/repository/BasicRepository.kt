package com.example.kotlinbackgroundprocessing.repository

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinbackgroundprocessing.app.App
import com.example.kotlinbackgroundprocessing.app.PhotosUtils
import com.example.kotlinbackgroundprocessing.service.PhotosJobService

object BasicRepository : Repository {
    private val photosLiveData = MutableLiveData<List<String>>()
    private val bannerLiveData = MutableLiveData<String>()

    init {
        scheduleFetchJob()
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

    private fun scheduleFetchJob() {
        val jobScheduler = App.getAppContext().getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val jobInfo = JobInfo.Builder(1000,
            ComponentName(App.getAppContext(), PhotosJobService::class.java))
            .setPeriodic(900000)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .build()

        jobScheduler.schedule(jobInfo)
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