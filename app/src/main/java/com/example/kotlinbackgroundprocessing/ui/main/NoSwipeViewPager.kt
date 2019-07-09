package com.example.kotlinbackgroundprocessing.ui.main

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NoSwipeViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    override fun onTouchEvent(ev: MotionEvent) = false

    override fun onInterceptTouchEvent(ev: MotionEvent) = false
}