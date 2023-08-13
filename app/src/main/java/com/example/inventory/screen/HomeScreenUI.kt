package com.example.inventory.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext

@Composable
fun HomeScreenUI() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column {
        Text(text = "My Inventory")
    }
}