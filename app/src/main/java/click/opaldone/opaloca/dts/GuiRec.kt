package click.opaldone.opaloca.dts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import click.opaldone.opaloca.dts.ViewMod
import click.opaldone.opaloca.loga.show_log

class GuiRec : BroadcastReceiver() {
    private lateinit var vm: ViewMod

    override fun onReceive(context: Context?, intent: Intent?) {
        val msg: String? = intent?.getStringExtra("log_mes")

        if (msg == null) return;

        vm.addLogis(msg)
    }

    fun setVm(vmin: ViewMod) {
        vm = vmin
    }
}
