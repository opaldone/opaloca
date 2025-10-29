package click.opaldone.opaloca.ui.nav

import androidx.compose.runtime.MutableState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.foundation.background
import androidx.compose.ui.window.PopupProperties
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.foundation.layout.IntrinsicSize

import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.TextRange
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.em
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.runtime.Composable
import android.content.Context
import android.content.Intent
import androidx.navigation.NavController
import click.opaldone.opaloca.dts.ShareTools
import click.opaldone.opaloca.R
import click.opaldone.opaloca.loga.show_log

class SettingsScreen(
    val ctx: Context,
    val nc: NavController,
    val back: String,
) {
    val sha = ShareTools(ctx)

    fun saveSettings(urlin: String, nikin: String, ridin: String) {
        sha.set_host_url(urlin)
        sha.set_nik(nikin)
        sha.set_roomid(ridin)

        nc.navigate(back)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ExpaList(state: MutableState<String>, list_items: Array<String>, lbl: String) {
        var expa by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expa,
            onExpandedChange = { expa = !expa },
        ) {
            OutlinedTextField(
                modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable)
                .fillMaxWidth()
                .padding(5.dp),
                shape = RoundedCornerShape(7.dp),
                value = state.value,
                onValueChange = {
                    state.value = it
                    expa = true
                },
                label = {
                    Text(
                        text = lbl,
                        fontSize = 12.sp,
                        color = Color(0xff777777),
                        style = TextStyle(letterSpacing = 0.3.em)
                    )
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expa) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    unfocusedBorderColor = Color(0xfff0f0f0),
                    focusedBorderColor = Color(0xff2c5de5)
                )
            )
            DropdownMenu(
                modifier = Modifier
                .background(Color.White)
                .exposedDropdownSize(true),
                properties = PopupProperties(focusable = false),
                expanded = expa,
                onDismissRequest = { expa = false },
            ) {
                list_items.forEach { opt ->
                    DropdownMenuItem(
                        text = { Text(opt) },
                        onClick = {
                            state.value = opt
                            expa = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }

    @Composable
    fun Show() {
        var host_url = remember { mutableStateOf(sha.get_host_url()) }
        val host_list = stringArrayResource(R.array.host_list)
        var nik = remember{ mutableStateOf(sha.get_nik()) }
        var roomid: MutableState<String> = remember{ mutableStateOf(sha.get_roomid()) }

        Column() {
            Text(
                text = ctx.getString(R.string.r_set),
                color = Color(0xffffffff),
                fontSize = 24.sp,
                modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xff2c5de5))
                .padding(
                    start = 30.dp,
                    end = 30.dp,
                    top = 15.dp,
                    bottom = 15.dp
                )
            )

            Column(
                Modifier.fillMaxSize().padding(
                    start = 30.dp,
                    end = 30.dp,
                    top = 15.dp,
                    bottom = 15.dp
                )
            ) {
                ExpaList(host_url, host_list, "Host")

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    shape = RoundedCornerShape(7.dp),
                    value = nik.value,
                    onValueChange = {
                        nik.value = it
                    },
                    label = {
                        Text(
                            text = "NICKNAME",
                            fontSize = 12.sp,
                            color = Color(0xff777777),
                            style = TextStyle(letterSpacing = 0.3.em)
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        unfocusedBorderColor = Color(0xffe0e0e0),
                        focusedBorderColor = Color(0xff2c5de5)
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max),
                    verticalAlignment = Alignment.Top,
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .weight(7f),
                        shape = RoundedCornerShape(
                            topStart = 7.dp,
                            topEnd = 0.dp,
                            bottomStart = 7.dp,
                            bottomEnd = 0.dp
                        ),
                        value = roomid.value,
                        onValueChange = {
                            val seg = it.split("/")
                            var va = seg[0]
                            if (seg.size > 1) {
                                va = seg[1]
                            }

                            roomid.value = va
                        },
                        label = {
                            Text(
                                text = "ROOMID",
                                fontSize = 12.sp,
                                color = Color(0xff777777),
                                style = TextStyle(letterSpacing = 0.3.em)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            unfocusedBorderColor = Color(0xffe0e0e0),
                            focusedBorderColor = Color(0xff2c5de5)
                        )
                    )

                    // Button(
                    IconButton(
                        modifier = Modifier
                            .padding(top = 7.dp)
                            .fillMaxHeight()
                            .weight(1f),
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Color(0xff2c5de5),
                            containerColor = Color(0xffd7d7d7)
                        ),
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 7.dp,
                            bottomStart =0.dp,
                            bottomEnd = 7.dp
                        ),
                        onClick = { roomid.value = "" }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = "Erase",
                        )
                    }
                }

                Spacer(Modifier.padding(16.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color(0xffffffff),
                        containerColor = Color(0xff2c5de5)
                    ),
                    shape = RoundedCornerShape(7.dp),
                    onClick = { saveSettings(host_url.value, nik.value, roomid.value) }
                ) {
                    Text(
                        text = ctx.getString(R.string.apply),
                        modifier = Modifier
                        .padding(5.dp),
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}
