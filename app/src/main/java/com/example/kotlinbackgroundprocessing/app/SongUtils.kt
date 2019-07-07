package com.example.kotlinbackgroundprocessing.app

import android.content.Context
import android.util.Log
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.net.URL

object SongUtils {
    private const val TAG = "SongUtils"

    fun download(urlAddress: String) {
        try {
            val songDir = songDirectory()
            if (!songDir.exists()) {
                songDir.mkdirs()
            }

            val file = songFile()
            val url = URL(urlAddress)

            val inputStream = BufferedInputStream(url.openStream())
            val outputStream = FileOutputStream(file)

            val data = ByteArray(1024)

            var total = 0L
            var count = inputStream.read(data)
            while (count != -1) {
                total++
                Log.i(TAG, "$total")

                outputStream.write(data, 0, count)
                count = inputStream.read(data)
            }

            outputStream.flush()
            outputStream.close()
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun songFile(): File {
        return File(songDirectory(), Constants.SONG_FILENAME)
    }

    private fun songDirectory(): File {
        return App.getAppContext().getDir(Constants.SONG_DIRECTORY, Context.MODE_PRIVATE)
    }
}