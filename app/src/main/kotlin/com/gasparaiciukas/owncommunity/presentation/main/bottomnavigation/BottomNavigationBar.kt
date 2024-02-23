package com.gasparaiciukas.owncommunity.presentation.main.bottomnavigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.gasparaiciukas.owncommunity.presentation.destinations.CreateScreenDestination
import com.gasparaiciukas.owncommunity.presentation.destinations.HomeScreenDestination
import com.gasparaiciukas.owncommunity.presentation.utils.navigation.BottomNavigationDestination
import com.gasparaiciukas.owncommunity.presentation.utils.theme.Colors
import com.ramcosta.composedestinations.utils.currentDestinationAsState

@Composable
fun BottomNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier,
    onHomeDestinationRepeatClick: () -> Unit = {},
) {
    val currentDestination = navController.currentDestinationAsState()
    NavigationBar(
        modifier =
            Modifier
                .height(84.dp)
                .then(modifier),
        containerColor = Colors.PureBlack,
        windowInsets = WindowInsets.navigationBars,
    ) {
        BottomNavigationDestination.entries.forEach { destination ->
            NavigationBarItem(
                selected = currentDestination.value == destination.direction,
                onClick = {
                    if (destination.direction == HomeScreenDestination &&
                        currentDestination.value == HomeScreenDestination
                    ) {
                        onHomeDestinationRepeatClick()
                        return@NavigationBarItem
                    }
                    navController.navigate(destination.direction.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
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
                colors =
                    if (destination.direction == CreateScreenDestination) {
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
