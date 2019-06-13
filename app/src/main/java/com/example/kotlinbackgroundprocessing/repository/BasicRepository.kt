package com.example.kotlinbackgroundprocessing.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinbackgroundprocessing.app.PhotosUtils

object BasicRepository : Repository {
    private val photosLiveData = MutableLiveData<List<String>>()
    private val bannerLiveData = MutableLiveData<String>()

    override fun getPhotos(): LiveData<List<String>> {
        FetchPhotosAsyncTask { photos ->
            photosLiveData.value = photos
        }.execute()

        return photosLiveData
    }

    override fun getBanner(): LiveData<String> {
        fetchBanner()
        return bannerLiveData
    }

    private fun fetchBanner() {
        val runnable = Runnable {
            val photosString = PhotosUtils.photoJsonString()
            val banner = PhotosUtils.bannerUrlFromJsonString(photosString)

            if (banner != null) {
                bannerLiveData.postValue(banner)
            }
        }

        val thread = Thread(runnable)
        // thread.start()
    }

    private class FetchPhotosAsyncTask(val callback: (List<String>) -> Unit)
        : AsyncTask<Void, Void, List<String>>() {

        override fun doInBackground(vararg params: Void?): List<String>? {
            val photosString = PhotosUtils.photoJsonString()
            return PhotosUtils.photoUrlsFromJsonString(photosString)
        }

        override fun onPostExecute(result: List<String>?) {
            if (result != null) {
                callback(result)
            }
        }
    }
}