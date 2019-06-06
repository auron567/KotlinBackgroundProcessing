package com.example.kotlinbackgroundprocessing.repository

import androidx.lifecycle.LiveData

interface Repository {

    fun getPhotos(): LiveData<List<String>>

    fun getBanner(): LiveData<String>
}