package click.opaldone.opaloca.dts

import android.content.Context
import androidx.core.content.edit
import click.opaldone.opaloca.R
import click.opaldone.opaloca.loga.show_log

class ShareTools(
    private val ctx: Context
) {
    companion object {
        const val PREFS = "opaloca_prefs"
        const val KURL = "host_url"
        const val KNIK = "nik"
        const val KRID = "roomid"
    }

    private val sha = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun get_host_url(): String {
        return sha.getString(KURL, ctx.getString(R.string.host_url))!!
    }

    fun set_host_url(nu: String) {
        sha.edit {
            putString(KURL, nu)
        }
    }

    fun set_nik(ni: String) {
        sha.edit {
            putString(KNIK, ni)
        }
    }

    fun set_roomid(ri: String) {
        sha.edit {
            putString(KRID, ri)
        }
    }

    fun get_map_url(): String {
        val _url = get_host_url()
        val po = ctx.getString(R.string.map_port)
        val pa = ctx.getString(R.string.map_path)
        return "https://$_url:$po/$pa"
    }

    fun get_nik(): String {
        return sha.getString(KNIK, ctx.getString(R.string.nik))!!
    }

    fun get_roomid(): String {
        return sha.getString(KRID, ctx.getString(R.string.chat_roomid))!!
    }

    fun get_chat_url(): String {
        val _url = get_host_url()
        val rid = get_roomid()

        var po = ctx.getString(R.string.chat_port)
        if (po.length > 0) {
            po = ":$po"
        }

        if (rid.length == 0) {
            return "https://$_url$po"
        }

        val ni = get_nik()

        val pa = ctx.getString(R.string.chat_path, rid, ni)
        return "https://$_url$po/$pa"
    }
}
