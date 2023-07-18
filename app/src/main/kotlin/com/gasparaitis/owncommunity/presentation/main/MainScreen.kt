package com.gasparaitis.owncommunity.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.gasparaitis.owncommunity.presentation.NavGraphs
import com.gasparaitis.owncommunity.presentation.appCurrentDestinationAsState
import com.gasparaitis.owncommunity.presentation.destinations.AlertsScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.CreateScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.Destination
import com.gasparaitis.owncommunity.presentation.destinations.HomeScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.PostScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.ProfileScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.SearchScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.StoryScreenDestination
import com.gasparaitis.owncommunity.presentation.startAppDestination
import com.gasparaitis.owncommunity.presentation.utils.theme.AppTheme
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentDestination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    BottomNavigationBar(navController = navController)
                },
            ) { paddingValues ->
                DestinationsNavHost(
                    modifier = Modifier.padding(paddingValues),
                    navGraph = NavGraphs.root,
                    navController = navController,
                    startRoute = HomeScreenDestination,
                )
            }
        }
    }
}
fun shouldShowBottomNavigationBar(destination: Destination) = when (destination) {
    AlertsScreenDestination -> TODO()
    CreateScreenDestination -> TODO()
    HomeScreenDestination -> TODO()
    PostScreenDestination -> TODO()
    ProfileScreenDestination -> TODO()
    SearchScreenDestination -> TODO()
    StoryScreenDestination -> TODO()
    else -> true
}
