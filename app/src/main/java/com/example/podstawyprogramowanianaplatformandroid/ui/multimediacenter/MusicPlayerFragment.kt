package com.example.podstawyprogramowanianaplatformandroid.ui.multimediacenter

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.podstawyprogramowanianaplatformandroid.R
import com.example.podstawyprogramowanianaplatformandroid.ui.multimediacenter.adapters.MusicPlayerAdapter
import kotlinx.android.synthetic.main.fragment_music_player.*
import java.lang.reflect.Field

class MusicPlayerFragment : Fragment(R.layout.fragment_music_player) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_music_player.adapter = MusicPlayerAdapter().apply {
            val fields: Array<Field> = R.raw::class.java.fields

            setItems(fields.indices.map {
                Triple(fields[it].name, fields[it].getInt(fields[it]), it)
            })

            setonItemClickListener {
                findNavController().navigate(
                    R.id.action_musicPlayerFragment_to_playerFragment,
                    bundleOf(
                        "MusicPlayerId" to it.second,
                        "MusicPlayerString" to it.first,
                        "MusicPlayerIndicesId" to it.third
                    )
                )
            }
        }
    }
}