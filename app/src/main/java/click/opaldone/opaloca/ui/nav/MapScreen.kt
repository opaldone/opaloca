package click.opaldone.opaloca.ui.nav

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import android.webkit.WebView
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.webkit.WebChromeClient;
import android.webkit.WebSettings
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.DisposableEffect
import click.opaldone.opaloca.dts.ShareTools
import click.opaldone.opaloca.loga.show_log

class MapScreen() {
    @Composable
    fun Show(url_in: String) {
        var webView: WebView? by remember { mutableStateOf(null) }

        AndroidView(
            factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    webViewClient = WebViewClient()
                    webChromeClient = WebChromeClient()

                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        loadWithOverviewMode = true
                        useWideViewPort = true
                        javaScriptCanOpenWindowsAutomatically = false
                        cacheMode = WebSettings.LOAD_NO_CACHE
                    }

                    loadUrl("about:blank")
                    loadUrl(url_in)
                }
            },
            update = { view ->
                webView = view
            }
        )

        DisposableEffect(Unit) {
            onDispose {
                webView?.let { it.cleanup() }
            }
        }
    }
}
