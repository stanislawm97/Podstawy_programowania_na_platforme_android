package com.example.podstawyprogramowanianaplatformandroid.ui.multimediacenter

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.podstawyprogramowanianaplatformandroid.R
import kotlinx.android.synthetic.main.fragment_website.*

class WebsiteFragment : Fragment(R.layout.fragment_website) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                ll_loading_animation.animate()
                    .setDuration(600)
                    .alpha(0f)
                    .scaleY(0f)
                    .scaleX(0f)
                    .withEndAction {
                        ll_loading_animation.visibility = View.GONE
                    }.start()
            }
        }
        webview.loadUrl("https://www.wiea.uz.zgora.pl")
    }
}