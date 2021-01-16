package com.example.podstawyprogramowanianaplatformandroid.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.podstawyprogramowanianaplatformandroid.R

class SlideshowFragment : Fragment(R.layout.fragment_slideshow) {

    private lateinit var slideshowViewModel: SlideshowViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        slideshowViewModel = ViewModelProvider(this).get(SlideshowViewModel::class.java)
    }
}