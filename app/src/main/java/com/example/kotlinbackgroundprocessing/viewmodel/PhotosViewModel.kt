package com.example.kotlinbackgroundprocessing.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinbackgroundprocessing.app.Injection

class PhotosViewModel : ViewModel() {
    private val repository = Injection.provideRepository()

    fun getPhotos(): LiveData<List<String>> {
        return repository.getPhotos()
    }

    fun getBanner(): LiveData<String> {
        return repository.getBanner()
    }
}