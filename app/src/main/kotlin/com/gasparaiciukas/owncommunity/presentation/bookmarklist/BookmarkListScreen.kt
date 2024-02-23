package com.gasparaiciukas.owncommunity.presentation.bookmarklist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun BookmarkListScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Bookmark List Screen")
    }
}
