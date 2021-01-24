package com.example.podstawyprogramowanianaplatformandroid.ui.multimediacenter

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.podstawyprogramowanianaplatformandroid.R
import kotlinx.android.synthetic.main.fragment_sketchbook.*


class SketchbookFragment : Fragment(R.layout.fragment_sketchbook) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInk()

        bt_black.setOnClickListener {
            ink.setColor(ResourcesCompat.getColor(resources, android.R.color.black, null))
        }

        bt_holo_blue_bright.setOnClickListener {
            ink.setColor(
                ResourcesCompat.getColor(
                    resources,
                    android.R.color.holo_blue_bright,
                    null
                )
            )
        }

        bt_holo_green_dark.setOnClickListener {
            ink.setColor(ResourcesCompat.getColor(resources, android.R.color.holo_green_dark, null))
        }

        bt_holo_orange_dark.setOnClickListener {
            ink.setColor(
                ResourcesCompat.getColor(
                    resources,
                    android.R.color.holo_orange_dark,
                    null
                )
            )
        }

        bt_holo_red_dark.setOnClickListener {
            ink.setColor(ResourcesCompat.getColor(resources, android.R.color.holo_red_dark, null))
        }

        bt_2.setOnClickListener {
            ink.setMinStrokeWidth(2f)
            ink.setMaxStrokeWidth(2f)
        }

        bt_4.setOnClickListener {
            ink.setMinStrokeWidth(4f)
            ink.setMaxStrokeWidth(4f)
        }

        bt_6.setOnClickListener {
            ink.setMinStrokeWidth(6f)
            ink.setMaxStrokeWidth(6f)
        }

        bt_8.setOnClickListener {
            ink.setMinStrokeWidth(8f)
            ink.setMaxStrokeWidth(8f)
        }

        bt_10.setOnClickListener {
            ink.setMinStrokeWidth(10f)
            ink.setMaxStrokeWidth(10f)
        }
    }

    private fun initInk() {
        ink.setColor(ResourcesCompat.getColor(resources, android.R.color.black, null))
        ink.setMinStrokeWidth(2f)
        ink.setMaxStrokeWidth(2f)
    }
}