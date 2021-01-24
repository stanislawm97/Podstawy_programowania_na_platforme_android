package com.example.podstawyprogramowanianaplatformandroid.ui.multimediacenter

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.podstawyprogramowanianaplatformandroid.R
import kotlinx.android.synthetic.main.fragment_player.*
import java.lang.reflect.Field

class PlayerFragment : Fragment(R.layout.fragment_player) {

    private lateinit var music: MediaPlayer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val musicPlayerId = arguments?.getInt("MusicPlayerId")
        val musicPlayerString = arguments?.getString("MusicPlayerString")
        val musicPlayerIndicesId = arguments?.getInt("MusicPlayerIndicesId")

        val fields: Array<Field> = R.raw::class.java.fields

        if (musicPlayerId == null || musicPlayerString == null || musicPlayerIndicesId == null) {
            Toast.makeText(
                requireActivity(),
                getString(R.string.something_goes_wrong),
                Toast.LENGTH_LONG
            ).show()
            findNavController().popBackStack()
        } else {
            tv_title.text = musicPlayerString
            music = MediaPlayer.create(requireActivity(), musicPlayerId)
            music.start()
        }

        ibt_play.setOnClickListener {
            music.start()
            lav_player.resumeAnimation()
            ibt_play.animate().scaleY(0f).scaleX(0f).alpha(0f).setDuration(200).withEndAction {
                ibt_play.visibility = View.GONE
                ibt_pause.animate().scaleY(1f).scaleX(1f).alpha(1f).setDuration(200)
                    .withStartAction {
                        ibt_pause.visibility = View.VISIBLE
                    }.start()
            }.start()
        }

        ibt_pause.setOnClickListener {
            music.pause()
            lav_player.pauseAnimation()
            ibt_pause.animate().scaleY(0f).scaleX(0f).alpha(0f).setDuration(200).withEndAction {
                ibt_pause.visibility = View.GONE
                ibt_play.animate().scaleY(1f).scaleX(1f).alpha(1f).setDuration(200)
                    .withStartAction {
                        ibt_play.visibility = View.VISIBLE
                    }.start()
            }.start()
        }

        ibt_next.setOnClickListener { _ ->
            val data: List<Triple<String, Int, Int>> = fields.indices.map {
                Triple(fields[it].name, fields[it].getInt(fields[it]), it)
            }
            val actualMusic = data.find {
                it.second == musicPlayerId
            }

            if (data.indexOf(actualMusic) + 1 <= data.size - 1) {
                val next = data[data.indexOf(actualMusic) + 1]

                findNavController().navigate(
                    R.id.playerFragment,
                    bundleOf(
                        "MusicPlayerId" to next.second,
                        "MusicPlayerString" to next.first,
                        "MusicPlayerIndicesId" to next.third
                    ),
                    NavOptions.Builder()
                        .setPopUpTo(R.id.musicPlayerFragment, false)
                        .setEnterAnim(R.anim.fragment_open_enter)
                        .setExitAnim(R.anim.fragment_open_exit)
                        .setPopEnterAnim(R.anim.fragment_close_enter)
                        .setPopExitAnim(R.anim.fragment_close_exit)
                        .build()
                )
            }
        }

        ibt_previous.setOnClickListener {
            val data: List<Triple<String, Int, Int>> = fields.indices.map {
                Triple(fields[it].name, fields[it].getInt(fields[it]), it)
            }
            val actualMusic = data.find {
                it.second == musicPlayerId
            }

            if (data.indexOf(actualMusic) - 1 >= 0) {
                val next = data[data.indexOf(actualMusic) - 1]

                findNavController().navigate(
                    R.id.playerFragment,
                    bundleOf(
                        "MusicPlayerId" to next.second,
                        "MusicPlayerString" to next.first,
                        "MusicPlayerIndicesId" to next.third
                    ),
                    NavOptions.Builder()
                        .setPopUpTo(R.id.musicPlayerFragment, false)
                        .setEnterAnim(R.anim.fragment_open_enter)
                        .setExitAnim(R.anim.fragment_open_exit)
                        .setPopEnterAnim(R.anim.fragment_close_enter)
                        .setPopExitAnim(R.anim.fragment_close_exit)
                        .build()
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()
        music.stop()
    }
}