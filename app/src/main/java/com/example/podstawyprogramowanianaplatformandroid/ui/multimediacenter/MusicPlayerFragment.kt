package com.example.podstawyprogramowanianaplatformandroid.ui.multimediacenter

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.podstawyprogramowanianaplatformandroid.R

class MusicPlayerFragment : Fragment(R.layout.fragment_music_player) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val music = MediaPlayer.create(requireActivity(), R.raw.bagaze)
        music.start()
    }
}