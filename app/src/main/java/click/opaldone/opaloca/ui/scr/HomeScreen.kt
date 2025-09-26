package click.opaldone.opaloca.ui.scr

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Color

class HomeScreen(
    val sharep: SharedPreferences
) {
    @Composable
    fun Show() {
        val wsname = sharep.getString("ws_name", "empty")!!
        val wsurl = sharep.getString("ws_url", "empty")!!

        Column(
            Modifier.fillMaxSize().systemBarsPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row() {
                Text(
                    text = "Nickname",
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                    color = Color(0xff575757),
                    modifier = Modifier.width(100.dp)
                )
                Text(
                    text = "$wsname",
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                    color = Color(0xff2c5de5),
                    modifier = Modifier.width(200.dp)
                )
            }
            Row() {
                Text(
                    text = "WS URL",
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                    color = Color(0xff575757),
                    modifier = Modifier.width(100.dp)
                )
                Text(
                    text = "$wsurl",
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                    color = Color(0xff2c5de5),
                    modifier = Modifier.width(200.dp)
                )
            }
        }
    }
}
