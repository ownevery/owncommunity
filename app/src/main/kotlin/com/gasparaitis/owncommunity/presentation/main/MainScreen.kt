package com.gasparaitis.owncommunity.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.gasparaitis.owncommunity.presentation.NavGraphs
import com.gasparaitis.owncommunity.presentation.appCurrentDestinationAsState
import com.gasparaitis.owncommunity.presentation.destinations.Destination
import com.gasparaitis.owncommunity.presentation.destinations.HomeScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.StoryScreenDestination
import com.gasparaitis.owncommunity.presentation.utils.extensions.componentActivity
import com.gasparaitis.owncommunity.presentation.utils.theme.AppTheme
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(LocalContext.current.componentActivity)
) {
    val navController = rememberNavController()
    val currentDestination = navController.appCurrentDestinationAsState()
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    if (shouldShowBottomNavigationBar(currentDestination.value)) {
                        BottomNavigationBar(
                            navController = navController,
                            onHomeDestinationRepeatClick = { viewModel.onEvent(MainEvent.OnHomeIconRepeatClick) },
                        )
                    }
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
fun shouldShowBottomNavigationBar(destination: Destination?) = when (destination) {
    StoryScreenDestination -> false
    else -> true
}
