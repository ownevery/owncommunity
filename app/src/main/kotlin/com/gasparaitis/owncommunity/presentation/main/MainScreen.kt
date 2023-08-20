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
import com.gasparaitis.owncommunity.presentation.destinations.AlertsScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.CreateScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.Destination
import com.gasparaitis.owncommunity.presentation.destinations.HomeScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.PostScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.ProfileScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.SearchScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.StoryScreenDestination
import com.gasparaitis.owncommunity.presentation.home.HomeAction
import com.gasparaitis.owncommunity.presentation.home.HomeViewModel
import com.gasparaitis.owncommunity.presentation.search.SearchAction
import com.gasparaitis.owncommunity.presentation.search.SearchViewModel
import com.gasparaitis.owncommunity.presentation.utils.extensions.componentActivity
import com.gasparaitis.owncommunity.presentation.utils.theme.AppTheme
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
fun MainScreen(
    homeViewModel: HomeViewModel = hiltViewModel(LocalContext.current.componentActivity),
    searchViewModel: SearchViewModel = hiltViewModel(LocalContext.current.componentActivity),
) {
    val navController = rememberNavController()
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    BottomNavigationBar(
                        navController = navController,
                        onHomeDestinationRepeatClick = { homeViewModel.onAction(HomeAction.OnHomeIconRepeatClick) },
                        onSearchDestinationRepeatClick = { searchViewModel.onAction(SearchAction.OnSearchIconRepeatClick) },
                    )
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
