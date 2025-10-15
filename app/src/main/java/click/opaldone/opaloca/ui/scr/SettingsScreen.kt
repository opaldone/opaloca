package click.opaldone.opaloca.ui.scr

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.compose.runtime.Composable
import android.content.Context
import android.content.SharedPreferences
import androidx.navigation.NavController
import click.opaldone.opaloca.R
import click.opaldone.opaloca.dts.Client
import click.opaldone.opaloca.loga.show_log

class SettingsScreen(
    val ctx: Context,
    val nc: NavController,
    val back: String,
    val sharep: SharedPreferences
) {
    fun getWsURL(): String {
        return sharep.getString("ws_url", ctx.getString(R.string.ws_url))!!
    }

    fun getWsName(): String {
        return sharep.getString("ws_name", ctx.getString(R.string.ws_name))!!
    }

    fun saveWsURL(wsurl: String, wsname: String) {
        sharep.edit {
            putString("ws_url", wsurl)
            putString("ws_name", wsname)
        }

        nc.navigate(back)
    }

    @Composable
    fun Show() {
        var wsu by remember{ mutableStateOf(getWsURL()) }
        var wsn by remember{ mutableStateOf(getWsName()) }

        Column(
            Modifier.fillMaxSize().systemBarsPadding()
        ){
            OutlinedTextField(
                value = wsu,
                modifier = Modifier.fillMaxWidth().padding(5.dp),
                textStyle = TextStyle(fontSize = 19.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xffeeeeee),
                    focusedContainerColor = Color.White,
                ),
                placeholder = { Text("WebSocket URL") },
                onValueChange = { nt -> wsu = nt }
            )
            OutlinedTextField(
                value = wsn,
                modifier = Modifier.fillMaxWidth().padding(5.dp),
                textStyle = TextStyle(fontSize = 19.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xffeeeeee),
                    focusedContainerColor = Color.White,
                ),
                placeholder = { Text("Nikname") },
                onValueChange = { nt -> wsn = nt }
            )

            Spacer(Modifier.padding(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth().padding(5.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xffffffff),
                    containerColor = Color(0xff2c5de5)
                ),
                shape = RoundedCornerShape(7.dp),
                onClick = { saveWsURL(wsu, wsn) }
            ) {
                Text(ctx.getString(R.string.save), fontSize = 18.sp)
            }
        }
    }
}
