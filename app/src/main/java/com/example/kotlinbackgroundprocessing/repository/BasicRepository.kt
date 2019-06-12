package com.example.kotlinbackgroundprocessing.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinbackgroundprocessing.app.PhotosUtils

object BasicRepository : Repository {
    private val photosLiveData = MutableLiveData<List<String>>()
    private val bannerLiveData = MutableLiveData<String>()

    override fun getPhotos(): LiveData<List<String>> {
        fetchJsonData()
        return photosLiveData
    }

    override fun getBanner(): LiveData<String> {
        return bannerLiveData
    }

    private fun fetchJsonData() {
        val runnable = Runnable {
            val photosString = PhotosUtils.photoJsonString()
            val photos = PhotosUtils.photoUrlsFromJsonString(photosString)

            if (photos != null) {
                photosLiveData.postValue(photos)
            }
        }

        val thread = Thread(runnable)
        thread.start()
    }
}