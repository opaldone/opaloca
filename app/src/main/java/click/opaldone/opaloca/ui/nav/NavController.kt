package click.opaldone.opaloca.ui.nav

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.compose.ui.res.stringResource
import click.opaldone.opaloca.dts.ShareTools
import click.opaldone.opaloca.R
import click.opaldone.opaloca.loga.show_log

class NavController(val ctx: Context) {
    data class BarItem(
        val title: String,
        val image: ImageVector,
        val route: String
    )

    sealed class NavRoutes(val route: String) {
        object Mapa : NavRoutes("mapa")
        object Chat : NavRoutes("chat")
        object Settings : NavRoutes("settings")
    }

    @Composable
    fun BottomNavBar(navController: NavController) {
        val bars = listOf(
            BarItem(
                title = stringResource(R.string.r_map),
                image = Icons.Rounded.LocationOn,
                route = NavRoutes.Mapa.route
            ),
            BarItem(
                title = stringResource(R.string.r_chat),
                image = Icons.Rounded.Face,
                route = NavRoutes.Chat.route
            ),
            BarItem(
                title = stringResource(R.string.r_set),
                image = Icons.Rounded.Settings,
                route = NavRoutes.Settings.route
            )
        )

        NavigationBar {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route

            bars.forEach { navItem ->
                NavigationBarItem(
                    selected = currentRoute == navItem.route,
                    onClick = {
                        navController.navigate(navItem.route) {
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(imageVector = navItem.image,
                        contentDescription = navItem.title)
                    },
                    label = {
                        Text(text = navItem.title)
                    }
                )
            }
        }
    }

    private fun startWithRoomId(roomid: String?): String {
        var ret = NavRoutes.Mapa.route

        if (roomid != null) {
            ret = NavRoutes.Chat.route
            (ShareTools(ctx)).set_roomid(roomid)
        }

        return ret
    }

    @Composable
    fun NavMain(roomid: String?) {
        val nc = rememberNavController()

        val start_route = startWithRoomId(roomid)

        Column() {
            NavHost(nc, startDestination = start_route, modifier = Modifier.weight(1f)) {
                composable(NavRoutes.Mapa.route) {
                    ScrMapa()
                }
                composable(NavRoutes.Chat.route) {
                    ScrChat()
                }
                composable(NavRoutes.Settings.route) {
                    ScrSettings(nc)
                }
            }

            BottomNavBar(navController = nc)
        }
    }

    @Composable
    fun ScrMapa() {
        val sha = ShareTools(ctx)
        var murl = sha.get_map_url()
        val scr = MapScreen()
        scr.Show(murl)
    }

    @Composable
    fun ScrChat() {
        val sha = ShareTools(ctx)
        var murl = sha.get_chat_url()

        show_log("murl = $murl")

        val scr = MapScreen()
        scr.Show(murl)
    }

    @Composable
    fun ScrSettings(nc: NavController) {
        val scr = SettingsScreen(ctx, nc, NavRoutes.Mapa.route)
        scr.Show()
    }
}
