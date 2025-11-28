package click.opaldone.opaloca.ui.nav

import android.os.Build
import android.webkit.WebView
import click.opaldone.opaloca.loga.show_log

fun WebView.stopJavaScriptTimers() {
    val jsCode = """
    try {
        var highestTimeoutId = setTimeout(function() {}, 0);
        for (var i = 0; i <= highestTimeoutId; i++) {
            clearTimeout(i);
            clearInterval(i);
        }

        if (window.cancelAnimationFrame) {
            var highestAnimationId = requestAnimationFrame(function() {});
            for (var j = 0; j <= highestAnimationId; j++) {
                cancelAnimationFrame(j);
            }
        }
    } catch(e) {
        console.log('Error stopping timers: ' + e.message);
    }
    """.trimIndent()

    evaluateJavascript(jsCode, null)
}

fun WebView.cleanup() {
    try {
        stopLoading()
        stopJavaScriptTimers()

        loadUrl("about:blank")

        clearHistory()
        clearCache(true)
        clearFormData()
    } catch (e: Exception) {
        show_log("WebView Error during cleanup ${e.message}")
    }
}
