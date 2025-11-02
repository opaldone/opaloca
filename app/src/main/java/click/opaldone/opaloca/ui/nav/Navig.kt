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
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.compose.ui.platform.LocalContext
import android.app.Activity
import click.opaldone.opaloca.MainActivity
import androidx.compose.ui.res.stringResource
import click.opaldone.opaloca.dts.ShareTools
import click.opaldone.opaloca.R
import click.opaldone.opaloca.loga.show_log

class Navig(val ctx: Context) {
    private lateinit var nc: NavHostController

    data class BarItem(
        val title: String,
        val image: ImageVector,
        val route: String
    )

    sealed class NavRoutes(val route: String) {
        object Mapa : NavRoutes("mapa")
        object Chat : NavRoutes("chat")
        object Settings : NavRoutes("settings")
        object Closeapp : NavRoutes("closeapp")
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
            ),
            BarItem(
                title = stringResource(R.string.r_closeapp),
                image = Icons.Rounded.Close,
                route = NavRoutes.Closeapp.route
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
                            // launchSingleTop = true
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
        var ret = NavRoutes.Settings.route

        if (roomid != null) {
            ret = NavRoutes.Chat.route
            (ShareTools(ctx)).set_roomid(roomid)
        }

        return ret
    }

    @Composable
    fun NavMain(roomid: String?) {
        nc = rememberNavController()

        val start_route = startWithRoomId(roomid)

        Column() {
            NavHost(nc, startDestination = start_route, modifier = Modifier.weight(1f)) {
                composable(NavRoutes.Mapa.route) {
                    ShowMapa()
                }
                composable(NavRoutes.Chat.route) {
                    ShowChat()
                }
                composable(NavRoutes.Settings.route) {
                    ShowSettings()
                }
                composable(NavRoutes.Closeapp.route) {
                    CloseApp()
                }
            }

            BottomNavBar(navController = nc)
        }
    }

    fun navMap() {
        nc.navigate(NavRoutes.Mapa.route)
    }

    fun setRoomid(roomid: String) {
        (ShareTools(ctx)).set_roomid(roomid)
    }

    @Composable
    fun CloseApp() {
        val activity = (LocalContext.current as? Activity)
        activity?.finishAndRemoveTask();
    }

    @Composable
    fun ShowMapa() {
        var murl = (ShareTools(ctx)).get_map_url()
        val scr = MapScreen(this)
        scr.Show(murl)
    }

    @Composable
    fun ShowChat() {
        var murl = (ShareTools(ctx)).get_chat_url()
        val scr = MapScreen(this)
        scr.Show(murl)
    }

    @Composable
    fun ShowSettings() {
        val scr = SettingsScreen(ctx, this)
        scr.Show()
    }
}
