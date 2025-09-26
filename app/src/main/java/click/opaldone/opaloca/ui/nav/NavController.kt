package click.opaldone.opaloca.ui.nav

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.compose.ui.res.stringResource
import android.content.SharedPreferences
import click.opaldone.opaloca.ui.scr.SettingsScreen
import click.opaldone.opaloca.ui.scr.HomeScreen
import click.opaldone.opaloca.ui.scr.LogsScreen
import click.opaldone.opaloca.R
import click.opaldone.opaloca.dts.ViewMod

class NavController(
    val ctx: Context,
    val sharep: SharedPreferences,
    val vm: ViewMod
) {
    data class BarItem(
        val title: String,
        val image: ImageVector,
        val route: String
    )

    sealed class NavRoutes(val route: String) {
        object Logs : NavRoutes("logs")
        object Settings : NavRoutes("settings")
        object Home : NavRoutes("home")
    }

    @Composable
    fun BottomNavBar(navController: NavController) {
        val bars = listOf(
            BarItem(
                title = stringResource(R.string.r_logs),
                image = Icons.Filled.Info,
                route = NavRoutes.Logs.route
            ),
            BarItem(
                title = stringResource(R.string.r_set),
                image = Icons.Filled.Settings,
                route = NavRoutes.Settings.route
            ),
            BarItem(
                title = stringResource(R.string.r_home),
                image = Icons.Filled.Home,
                route = NavRoutes.Home.route
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
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
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

    @Composable
    fun NavMain() {
        val nc = rememberNavController()

        Column(Modifier.padding(8.dp)) {
            NavHost(nc, startDestination = NavRoutes.Logs.route, modifier = Modifier.weight(1f)) {
                composable(NavRoutes.Logs.route) {
                    ScrLogs()
                }
                composable(NavRoutes.Settings.route) {
                    ScrSettings(nc)
                }
                composable(NavRoutes.Home.route) {
                    ScrHome()
                }
            }

            BottomNavBar(navController = nc)
        }
    }

    @Composable
    fun ScrHome() {
        val scr = HomeScreen(sharep)
        scr.Show()
    }

    @Composable
    fun ScrLogs() {
        val scr = LogsScreen(vm)
        scr.Show()
    }

    @Composable
    fun ScrSettings(nc: NavController) {
        val scr = SettingsScreen(ctx, nc, NavRoutes.Home.route, sharep)
        scr.Show()
    }
}
