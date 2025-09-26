package click.opaldone.opaloca.dts

import click.opaldone.opaloca.loga.show_log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import android.location.Location

data class Message(
    val tp: String,
    val content: String
) {
    companion object {
        const val RLOCA    = "rloca"
        const val ALOCA    = "aloca"
        const val SENDERHI = "sender_hi"
    }
}

data class Lo(
    val lat: Double,
    val lng: Double,
    val acc: Float
)

data class ClientHi(
    val cid: String,
    val nik: String,
    val issender: Boolean = true,
    val pos: Lo?
)

fun decMessage(msg: String): Message? {
    var ret: Message? = null

    try {
        val gson = Gson()
        ret = gson.fromJson(msg, Message::class.java)
    } catch(e: JsonSyntaxException) {
        show_log("Error decMessage: ${e.message}")
    }

    return ret
}

fun getJsonLoca(cid: String, nik: String?, loca: Location?): String {
    val gson = Gson()

    var lo_pos: Lo? = null;

    if (loca != null) {
        lo_pos = Lo(
            lat = loca.latitude,
            lng = loca.longitude,
            acc = loca.accuracy
        )
    }

    val ob = ClientHi(
        cid = cid,
        nik = nik ?: "",
        pos = lo_pos
    )

    val cojs = gson.toJson(ob);

    val msg = Message(
        tp = Message.ALOCA,
        content = cojs
    )

    val ret = gson.toJson(msg)

    return ret
}

fun getJsonHi(cid: String, nik: String?): String {
    val gson = Gson()

    val ob = ClientHi(
        cid = cid,
        nik = nik ?: "",
        pos = null
    )
    val cojs = gson.toJson(ob);

    val msg = Message(
        tp = Message.SENDERHI,
        content = cojs
    )

    val ret = gson.toJson(msg)

    return ret
}
