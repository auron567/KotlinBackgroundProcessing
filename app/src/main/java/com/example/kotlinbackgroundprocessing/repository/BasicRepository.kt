package com.example.kotlinbackgroundprocessing.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinbackgroundprocessing.app.PhotosUtils

object BasicRepository : Repository {

    override fun getPhotos(): LiveData<List<String>> {
        fetchJsonData()
        return MutableLiveData<List<String>>()
    }

    override fun getBanner(): LiveData<String> {
        return MutableLiveData<String>()
    }

    private fun fetchJsonData() {
        val runnable = Runnable {
            val photosString = PhotosUtils.photoJsonString()
            Log.i("BasicRepository", photosString)
        }
        val thread = Thread(runnable)
        thread.start()
    }
}