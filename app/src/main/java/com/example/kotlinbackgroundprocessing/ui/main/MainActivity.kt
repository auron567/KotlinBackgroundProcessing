package com.example.kotlinbackgroundprocessing.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.example.kotlinbackgroundprocessing.R
import com.example.kotlinbackgroundprocessing.ui.photos.PhotosFragment
import com.example.kotlinbackgroundprocessing.ui.song.SongFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_photos -> {
                    view_pager.setCurrentItem(0, false)
                    true
                }
                R.id.navigation_song -> {
                    view_pager.setCurrentItem(1, false)
                    true
                }
                else -> false
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
        setupViewPager()
    }

    private fun setupNavigation() {
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun setupViewPager() {
        view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment? {
                return when (position) {
                    0 -> PhotosFragment.newInstance()
                    1 -> SongFragment.newInstance()
                    else -> null
                }
            }

            override fun getCount() = 2
        }
    }
}