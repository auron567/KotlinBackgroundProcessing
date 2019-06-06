package com.example.kotlinbackgroundprocessing.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object BasicRepository : Repository {

    override fun getPhotos(): LiveData<List<String>> {
        return MutableLiveData<List<String>>()
    }

    override fun getBanner(): LiveData<String> {
        return MutableLiveData<String>()
    }
}