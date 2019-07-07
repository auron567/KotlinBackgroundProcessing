package com.example.kotlinbackgroundprocessing.ui.song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kotlinbackgroundprocessing.R
import com.example.kotlinbackgroundprocessing.app.Constants
import com.example.kotlinbackgroundprocessing.app.SongUtils
import com.example.kotlinbackgroundprocessing.service.DownloadIntentService
import kotlinx.android.synthetic.main.fragment_song.*

class SongFragment : Fragment() {

    companion object {
        fun newInstance() = SongFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!SongUtils.songFile().exists()) {
            play_button.isEnabled = false
            stop_button.isEnabled = false
        }

        download_button.setOnClickListener {
            DownloadIntentService.startActionDownload(view.context, Constants.SONG_URL)
        }
    }
}