package com.gasparaitis.owncommunity.presentation.story

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun StoryScreen(
    navController: NavController = rememberNavController(),
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Story Screen")
    }
}
