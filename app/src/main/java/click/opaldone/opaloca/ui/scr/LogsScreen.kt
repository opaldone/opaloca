package click.opaldone.opaloca.ui.scr

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*
import androidx.compose.material3.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import click.opaldone.opaloca.dts.ViewMod
import androidx.compose.runtime.*
import java.text.SimpleDateFormat
import androidx.compose.ui.text.style.LineBreak
import java.util.Date
import java.util.Locale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

class LogsScreen(private val vm: ViewMod) {
    @Composable
    fun Show() {
        val logis by remember { derivedStateOf { vm.logis } }

        Column(
            modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
        ) {
            LazyColumn(
                modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(logis) { loga ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = loga.txt,
                            modifier = Modifier
                                .weight(1f, fill = false)
                                .padding(
                                    end = 5.dp
                                ),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(loga.ts)),
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                    HorizontalDivider(color = Color(0xFFd7d7d7))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                ExtendedFloatingActionButton(
                    containerColor = Color(0xff2c5de5),
                    contentColor = Color(0xffffffff),
                    modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
                    icon = { Icon(Icons.Filled.Delete, contentDescription = "Clear") },
                    text = { Text(text = "Clear") },
                    onClick = { vm.clearLogis() }
                )
            }
        }
    }
}
