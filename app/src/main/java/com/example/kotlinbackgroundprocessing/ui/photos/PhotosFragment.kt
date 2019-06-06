package com.example.kotlinbackgroundprocessing.ui.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlinbackgroundprocessing.R
import com.example.kotlinbackgroundprocessing.viewmodel.PhotosViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_photos.*

class PhotosFragment : Fragment() {
    private lateinit var photosViewModel: PhotosViewModel
    private val adapter = PhotosAdapter(mutableListOf())

    companion object {
        fun newInstance() = PhotosFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photos, container, false)

        photosViewModel = ViewModelProviders.of(this).get(PhotosViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photos_recycler_view.layoutManager = GridLayoutManager(context, 2)
        photos_recycler_view.adapter = adapter

        photosViewModel.getBanner().observe(this, Observer<String> { banner ->
            Picasso.get().load(banner).into(banner_image_view)
        })

        photosViewModel.getPhotos().observe(this, Observer<List<String>> { photos ->
            adapter.updatePhotos(photos)
        })
    }
}