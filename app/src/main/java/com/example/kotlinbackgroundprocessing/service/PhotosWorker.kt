package com.example.kotlinbackgroundprocessing.service

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.kotlinbackgroundprocessing.app.PhotosUtils

class PhotosWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    companion object {
        private const val TAG = "PhotosWorker"
    }

    override fun doWork(): Result {
        val needsRetry = try {
            val jsonString = PhotosUtils.fetchJsonString()
            (jsonString == null)
        } catch (e: InterruptedException) {
            Log.e(TAG, "Error running worker: ${e.message}")
            true
        }

        if (needsRetry) {
            Log.i(TAG, "Work retried")
            return Result.retry()
        }

        Log.i(TAG, "Work finished")
        return Result.success()
    }
}