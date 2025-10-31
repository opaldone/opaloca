package click.opaldone.opaloca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import android.Manifest
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import android.content.Context
import click.opaldone.opaloca.ui.nav.Navig
import click.opaldone.opaloca.loga.show_log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.INTERNET,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.MODIFY_AUDIO_SETTINGS
            ),
            0
        )

        val roomid = intent.getStringExtra("roomid")

        setContent {
            NavStart(this, roomid)
        }
    }
}

@Composable
fun NavStart(ctx: Context, roomid: String?) {
    Navig(ctx).NavMain(roomid)
}
