package com.gasparaitis.owncommunity.presentation.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.gasparaitis.owncommunity.presentation.NavGraphs
import com.gasparaitis.owncommunity.presentation.appCurrentDestinationAsState
import com.gasparaitis.owncommunity.presentation.destinations.AlertListScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.BookmarkListScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.ChatListScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.CreateScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.Destination
import com.gasparaitis.owncommunity.presentation.destinations.EditProfileScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.HomeScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.PostScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.ProfileScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.SearchScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.StoryScreenDestination
import com.gasparaitis.owncommunity.presentation.main.bottomnavigation.BottomNavigationBar
import com.gasparaitis.owncommunity.presentation.main.bottomnavigation.BottomNavigationState
import com.gasparaitis.owncommunity.presentation.main.bottomnavigation.BottomNavigationViewModel
import com.gasparaitis.owncommunity.presentation.main.window.setDefaultSystemBars
import com.gasparaitis.owncommunity.presentation.utils.extensions.componentActivity
import com.gasparaitis.owncommunity.presentation.utils.extensions.mainActivity
import com.gasparaitis.owncommunity.presentation.utils.theme.AppTheme
import com.gasparaitis.owncommunity.presentation.utils.theme.Colors
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    bottomNavigationViewModel: BottomNavigationViewModel =
        hiltViewModel(LocalContext.current.componentActivity)
) {
    val navController = rememberNavController()
    val currentDestination by navController.appCurrentDestinationAsState()
    AppTheme {
        Scaffold(
            modifier =
                Modifier
                    .fillMaxSize()
                    .then(modifier),
            containerColor = Colors.DarkBlack,
            bottomBar = {
                if (isBottomNavigationBarShown(currentDestination)) {
                    BottomNavigationBar(
                        navController = navController,
                        onHomeDestinationRepeatClick = {
                            bottomNavigationViewModel.onEvent(
                                BottomNavigationState.OnHomeIconRepeatClick,
                            )
                        },
                    )
                }
            },
            contentWindowInsets = scaffoldContentWindowInsets(currentDestination),
        ) { paddingValues ->
            DestinationsNavHost(
                modifier = Modifier.padding(paddingValues),
                navGraph = NavGraphs.root,
                navController = navController,
                startRoute = HomeScreenDestination,
            )
        }
    }
    SystemBarsEffect()
}

@Composable
private fun SystemBarsEffect() {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        context.mainActivity.setDefaultSystemBars()
    }
}

private fun isBottomNavigationBarShown(destination: Destination?) =
    when (destination) {
        StoryScreenDestination -> false
        AlertListScreenDestination -> true
        ChatListScreenDestination -> true
        CreateScreenDestination -> true
        HomeScreenDestination -> true
        PostScreenDestination -> true
        ProfileScreenDestination -> true
        SearchScreenDestination -> true
        StoryScreenDestination -> true
        BookmarkListScreenDestination -> true
        EditProfileScreenDestination -> true
        null -> true
    }

@Composable
private fun scaffoldContentWindowInsets(destination: Destination?): WindowInsets =
    when (destination) {
        AlertListScreenDestination -> ScaffoldDefaults.contentWindowInsets
        ChatListScreenDestination -> ScaffoldDefaults.contentWindowInsets
        CreateScreenDestination -> ScaffoldDefaults.contentWindowInsets
        HomeScreenDestination -> ScaffoldDefaults.contentWindowInsets
        PostScreenDestination -> ScaffoldDefaults.contentWindowInsets
        ProfileScreenDestination -> WindowInsets.captionBar.add(WindowInsets.navigationBars)
        SearchScreenDestination -> ScaffoldDefaults.contentWindowInsets
        StoryScreenDestination -> WindowInsets(0, 0, 0, 0)
        BookmarkListScreenDestination -> ScaffoldDefaults.contentWindowInsets
        EditProfileScreenDestination -> ScaffoldDefaults.contentWindowInsets
        null -> ScaffoldDefaults.contentWindowInsets
    }
