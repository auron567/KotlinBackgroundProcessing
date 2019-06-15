package com.example.kotlinbackgroundprocessing.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class LogJobService : JobService() {

    companion object {
        private const val TAG = "LogJobService"
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        val runnable = Runnable {
            Thread.sleep(10000)
            jobFinished(params, false)
            Log.i(TAG, "Job finished: ${params?.jobId}")
        }

        Log.i(TAG, "Job started: ${params?.jobId}")
        Thread(runnable).start()
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.i(TAG, "Job stopped: ${params?.jobId}")
        return false
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate")
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy")
        super.onDestroy()
    }
}