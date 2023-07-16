package com.gasparaitis.owncommunity.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gasparaitis.owncommunity.ui.NavGraphs
import com.gasparaitis.owncommunity.ui.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
    ) { paddingValues ->
        DestinationsNavHost(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            navGraph = NavGraphs.root,
            navController = navController,
            startRoute = HomeScreenDestination,
        )
    }
}
