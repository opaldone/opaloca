package click.opaldone.opaloca.wsa

import android.app.Service
import okhttp3.WebSocket
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit
import android.content.Intent
import android.os.IBinder
import android.app.Notification
import androidx.core.app.NotificationCompat
import android.app.NotificationChannel
import android.app.NotificationManager
import click.opaldone.opaloca.R
import click.opaldone.opaloca.loga.show_log

class WsService : Service() {
    private lateinit var wscli: WebSocket
    private lateinit var okcli: OkHttpClient
    private val channelID = "opaloca_service_channel"
    private val channelName = "Opaloca Service"
    var ser_wsurl: String? = ""
    var ser_cid: String? = ""
    var ser_nik: String? = ""
    var action_log: String? = ""

    companion object {
        const val NOTIFICATION_ID = 18798
    }

    override fun onCreate() {
        super.onCreate()

        okcli = OkHttpClient.Builder()
            .pingInterval(20, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    override fun onStartCommand(si: Intent?, flags: Int, startId: Int): Int {
        ser_cid = si?.getStringExtra("cid")
        ser_wsurl = si?.getStringExtra("wsurl")
        ser_nik = si?.getStringExtra("nik")
        action_log = si?.getStringExtra("action_log")

        val notification = createNotif()
        startForeground(NOTIFICATION_ID, notification)

        connectWebSocket()

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        gui_rec("onDestroy")

        wscli.close(1000, "Service destroyed")
        okcli.dispatcher.executorService.shutdown()

        super.onDestroy()
    }

    private fun createNotif(): Notification {
        createChannel()

        return NotificationCompat.Builder(this, channelID)
            .setContentText("Opaloca started")
            .setSmallIcon(R.drawable.ic_launcher)
            .build()
    }

    private fun createChannel() {
        val channel = NotificationChannel(
            channelID,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )

        val service = getSystemService(NotificationManager::class.java)
        service.createNotificationChannel(channel)
    }

    fun connectWebSocket() {
        gui_rec("connecting to: $ser_wsurl")

        val request = Request.Builder()
            .url(ser_wsurl!!)
            .build()

        val listener = WsListe(this, ser_cid, ser_nik)
        wscli = okcli.newWebSocket(request, listener)
    }

    fun gui_rec(msg: String) {
        val intent = Intent()
        intent.action = action_log
        intent.putExtra("log_mes", msg)
        sendBroadcast(intent)
    }

    fun notif(msg: String) {
        val bld = NotificationCompat.Builder(this, channelID)
        .setContentText(msg)
        .setSmallIcon(R.drawable.ic_launcher)
        .build()

        val notif_man = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notif_man.notify(NOTIFICATION_ID, bld)

        gui_rec(msg)
    }

    fun sendMessage(msg: String) {
        wscli?.send(msg)
    }
}
