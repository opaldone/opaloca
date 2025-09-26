package click.opaldone.opaloca.dts

import android.content.Context
import android.content.SharedPreferences
import click.opaldone.opaloca.R
import java.util.UUID

class Client(
    private val ctx: Context,
    private val sharep: SharedPreferences
) {
    val _url = sharep.getString("ws_url", ctx.getString(R.string.ws_url))!!
    val cid: String = "se-" + UUID.randomUUID().toString().replace("-", "").substring(0, 7)
    val wsurl: String = "$_url/ws/$cid/1"
    val nik: String = sharep.getString("ws_name", ctx.getString(R.string.ws_name))!!
}
