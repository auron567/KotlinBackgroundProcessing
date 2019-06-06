package com.example.kotlinbackgroundprocessing.app

import com.example.kotlinbackgroundprocessing.repository.BasicRepository
import com.example.kotlinbackgroundprocessing.repository.Repository

object Injection {

    fun provideRepository(): Repository {
        return BasicRepository
    }
}