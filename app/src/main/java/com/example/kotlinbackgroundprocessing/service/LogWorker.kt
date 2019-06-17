package com.example.kotlinbackgroundprocessing.service

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class LogWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    companion object {
        private const val TAG = "LogWorker"
    }

    override fun doWork(): Result {
        Log.i(TAG, "Worker started")
        Thread.sleep(10000)
        Log.i(TAG, "Worker finished")
        return Result.success()
    }
}