package com.example.kotlinbackgroundprocessing.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.kotlinbackgroundprocessing.R
import com.example.kotlinbackgroundprocessing.ui.photos.PhotosFragment
import com.example.kotlinbackgroundprocessing.ui.song.SongFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.navigation_photos -> PhotosFragment.newInstance()
                R.id.navigation_song -> SongFragment.newInstance()
                else -> PhotosFragment.newInstance()
            }
            switchToFragment(fragment)
            true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        switchToFragment(PhotosFragment.newInstance())
    }

    private fun switchToFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment).commit()
    }
}