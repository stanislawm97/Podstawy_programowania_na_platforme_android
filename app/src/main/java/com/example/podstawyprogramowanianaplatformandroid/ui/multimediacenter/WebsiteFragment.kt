package com.example.podstawyprogramowanianaplatformandroid.ui.multimediacenter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.podstawyprogramowanianaplatformandroid.R
import kotlinx.android.synthetic.main.fragment_website.*

class WebsiteFragment : Fragment(R.layout.fragment_website) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webview.loadUrl("https://www.wiea.uz.zgora.pl")
    }
}