package click.opaldone.opaloca.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.PendingIntent
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import click.opaldone.opaloca.MainActivity
import click.opaldone.opaloca.R
import click.opaldone.opaloca.loga.show_log

class ChatReceiver : BroadcastReceiver() {
    private val notificationId = 802906
    private val channelID = "opaloca_channel_id"
    private val channelName = "opaloca_chanel"

    private fun createChannel(ctx: Context) {
        val channel = NotificationChannel(
            channelID,
            channelName,
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "The channel opaloca chat"
            lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        }

        val manager = ctx.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    private fun send_notif(ctx: Context, roomid: String) {
        createChannel(ctx)

        val notificationIntent = Intent(ctx, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        notificationIntent.putExtra("roomid", roomid)

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            ctx, 0, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(ctx, channelID)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentText("Tap to start chatting")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val man = NotificationManagerCompat.from(ctx)
        man.notify(notificationId, builder.build())
    }

    override fun onReceive(ctx: Context, intent: Intent) {
        val roomid = intent.getStringExtra("roomid")

        if (roomid == null) {
            return
        }

        send_notif(ctx, roomid!!)
    }
}
