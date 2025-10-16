package click.opaldone.opaloca.wsa

import okhttp3.WebSocketListener
import okhttp3.WebSocket
import okhttp3.Response
import android.content.Context.BATTERY_SERVICE
import android.os.Handler
import android.os.Looper
import android.os.BatteryManager;
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import androidx.core.app.ActivityCompat
import android.Manifest
import android.content.pm.PackageManager
import click.opaldone.opaloca.dts.getJsonHi
import click.opaldone.opaloca.dts.Message
import click.opaldone.opaloca.dts.decMessage
import click.opaldone.opaloca.dts.getJsonLoca
import click.opaldone.opaloca.loga.show_log

class WsListe(
    private val ser: WsService,
    private val ser_cid: String?,
    private val ser_nik: String?
) : WebSocketListener() {
    private val locl = LocationServices.getFusedLocationProviderClient(ser)

    override fun onOpen(webSocket: WebSocket, response: Response) {
        send_hi()
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        val msg = decMessage(text)
        msg?.let {
            when(msg.tp) {
                Message.RLOCA -> send_loca()
            }
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(1000, null)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        ser.gui_rec("Failure: ${t.message}")

        Handler(Looper.getMainLooper()).postDelayed({
            ser.connectWebSocket()
        }, 5000)
    }

    private fun send_hi() {
        val js = getJsonHi(ser.ser_cid!!, ser.ser_nik)
        ser.sendMessage(js)
    }

    private fun send_loca() {
        if (ActivityCompat.checkSelfPermission(
                ser,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ser.gui_rec("Permissions were not permitted")
            return
        }

        locl.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token,
        ).addOnSuccessListener { loca ->
            val bm = ser.getSystemService(BATTERY_SERVICE) as BatteryManager
            val bat = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

            val js = getJsonLoca(ser_cid!!, ser_nik, loca, bat)
            ser.sendMessage(js)
        }
    }
}
