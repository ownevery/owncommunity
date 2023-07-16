package com.gasparaitis.owncommunity.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gasparaitis.owncommunity.ui.NavGraphs
import com.gasparaitis.owncommunity.ui.appCurrentDestinationAsState
import com.gasparaitis.owncommunity.ui.destinations.CreateScreenDestination
import com.gasparaitis.owncommunity.ui.startAppDestination
import com.gasparaitis.owncommunity.utils.compose.ui.theme.Colors
import com.gasparaitis.owncommunity.utils.navigation.BottomNavigationDestination
import com.ramcosta.composedestinations.navigation.navigate

@Composable
fun BottomNavigationBar(
    navController: NavController,
) {
    val currentDestination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    NavigationBar(
        modifier = Modifier.height(64.dp),
        containerColor = Colors.PureBlack,
    ) {
        BottomNavigationDestination.values().forEach { destination ->
            NavigationBarItem(
                selected = currentDestination == destination.direction,
                onClick = {
                    navController.navigate(destination.direction) {
                        launchSingleTop = true
                    }
                },
                icon = {
                    if (destination.direction == CreateScreenDestination) {
                        Image(
                            modifier = Modifier.size(48.dp),
                            painter = painterResource(id = destination.icon),
                            contentDescription = stringResource(id = destination.label),
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = destination.icon),
                            contentDescription = stringResource(id = destination.label),
                        )
                    }
                },
                colors = if (destination.direction == CreateScreenDestination) {
                    NavigationBarItemDefaults.colors(
                        selectedIconColor = Colors.Transparent,
                        indicatorColor = Colors.Transparent,
                    )
                } else {
                    NavigationBarItemDefaults.colors(
                        selectedIconColor = Colors.PureWhite,
                        unselectedIconColor = Colors.LightGray,
                        indicatorColor = Colors.Transparent,
                    )
                },
            )
        }
    }
}
