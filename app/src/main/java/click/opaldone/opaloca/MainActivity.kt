package click.opaldone.opaloca

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import click.opaldone.opaloca.dts.ViewMod
import click.opaldone.opaloca.ui.nav.NavController
import click.opaldone.opaloca.dts.Client;
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import android.content.Context
import android.content.SharedPreferences
import android.content.Intent
import android.content.IntentFilter
import click.opaldone.opaloca.wsa.WsService
import click.opaldone.opaloca.dts.GuiRec

const val ACTION_LOG = "opaloca.gui.rec.logmes"

class MainActivity : ComponentActivity() {
    private lateinit var guir: GuiRec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ),
            0
        )

        val sharep = getSharedPreferences("opaloca_prefs", MODE_PRIVATE)
        val client = Client(this, sharep)

        guir = GuiRec()
        registerReceiver(guir, IntentFilter(ACTION_LOG), RECEIVER_EXPORTED)

        startWebSocketService(client)

        setContent {
            navStart(this, client, sharep, guir)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(guir)
    }

    private fun startWebSocketService(cl: Client) {
        val si = Intent(this, WsService::class.java)

        si.putExtra("cid", cl.cid)
        si.putExtra("wsurl", cl.wsurl)
        si.putExtra("nik", cl.nik)
        si.putExtra("action_log", ACTION_LOG)

        startForegroundService(si)
    }
}

@Composable
fun navStart(
    ctx: Context,
    client: Client,
    sharep:SharedPreferences,
    guir: GuiRec,
    vm: ViewMod = viewModel()
) {
    guir.setVm(vm)
    NavController(ctx, sharep, vm).NavMain()
}
