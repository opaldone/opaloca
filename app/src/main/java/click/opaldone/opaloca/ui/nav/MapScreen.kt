package click.opaldone.opaloca.ui.nav

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import android.webkit.WebView
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.webkit.WebChromeClient;
import android.webkit.WebSettings
import android.webkit.PermissionRequest
import android.Manifest
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.webkit.JavascriptInterface
import androidx.navigation.NavController
import android.widget.Toast
import android.view.Gravity
import androidx.compose.runtime.DisposableEffect
import click.opaldone.opaloca.dts.ShareTools
import click.opaldone.opaloca.loga.show_log

class MapScreen(private val nav: Navig) {
    class ChatWebViewInterface(private val nav: Navig) {
        @JavascriptInterface
        fun sendRoomid(roomid: String) {
            nav.setRoomid(roomid)
        }
    }

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

                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        loadWithOverviewMode = true
                        useWideViewPort = true
                        mediaPlaybackRequiresUserGesture = false
                        javaScriptCanOpenWindowsAutomatically = false
                        cacheMode = WebSettings.LOAD_NO_CACHE
                    }

                    addJavascriptInterface(ChatWebViewInterface(nav), "AndroidChatInterface")

                    webViewClient = WebViewClient()

                    webChromeClient = object : WebChromeClient() {
                        override fun onPermissionRequest(request: PermissionRequest) {
                            val requestedResources = request.resources
                            val permissionsToRequest = mutableListOf<String>()

                            for (resource in requestedResources) {
                                when (resource) {
                                    PermissionRequest.RESOURCE_VIDEO_CAPTURE -> {
                                        permissionsToRequest.add(Manifest.permission.CAMERA)
                                    }
                                    PermissionRequest.RESOURCE_AUDIO_CAPTURE -> {
                                        permissionsToRequest.add(Manifest.permission.RECORD_AUDIO)
                                    }
                                }
                            }

                            request.grant(request.resources)
                        }
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
