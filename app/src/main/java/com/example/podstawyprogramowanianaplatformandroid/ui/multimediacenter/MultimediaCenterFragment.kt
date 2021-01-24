package com.example.podstawyprogramowanianaplatformandroid.ui.multimediacenter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.podstawyprogramowanianaplatformandroid.R
import kotlinx.android.synthetic.main.fragment_multimedia_center.*

class MultimediaCenterFragment : Fragment(R.layout.fragment_multimedia_center) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bt_picture.setOnClickListener {
            findNavController().navigate(R.id.action_nav_multimedia_center_to_cameraFragment)
        }

        bt_sketchbook.setOnClickListener {
            findNavController().navigate(R.id.action_nav_multimedia_center_to_sketchbookFragment)
        }

        bt_music_player.setOnClickListener { }

        bt_website.setOnClickListener {
            findNavController().navigate(R.id.action_nav_multimedia_center_to_nav_website)
        }
    }
}