package click.opaldone.opaloca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import android.content.Context
import click.opaldone.opaloca.ui.nav.NavController
import click.opaldone.opaloca.loga.show_log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val roomid = intent.getStringExtra("roomid")

        setContent {
            NavStart(this, roomid)
        }
    }
}

@Composable
fun NavStart(ctx: Context, roomid: String?) {
    NavController(ctx).NavMain(roomid)
}
