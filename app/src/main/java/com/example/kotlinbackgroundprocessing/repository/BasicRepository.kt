package com.example.kotlinbackgroundprocessing.repository

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
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
        val handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message?) {
                val bundle = msg?.data
                val photos = bundle?.getStringArrayList("PHOTO_KEY")
                photosLiveData.value = photos
            }
        }

        val runnable = Runnable {
            val photosString = PhotosUtils.photoJsonString()
            val photos = PhotosUtils.photoUrlsFromJsonString(photosString)

            if (photos != null) {
                val message = Message()
                val bundle = Bundle()
                bundle.putStringArrayList("PHOTO_KEY", photos)
                message.data = bundle
                handler.sendMessage(message)
            }
        }

        val thread = Thread(runnable)
        thread.start()
    }
}