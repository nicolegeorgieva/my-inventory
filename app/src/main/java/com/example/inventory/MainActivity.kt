package com.example.inventory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.inventory.screen.HomeScreen
import com.example.inventory.screen.SettingsScreen
import com.example.inventory.ui.theme.InventoryTheme

sealed interface Screen {
    object Home : Screen
    object Settings : Screen
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InventoryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

                    when (currentScreen) {
                        Screen.Home -> HomeScreen(navigateTo = {
                            currentScreen = it
                        })

                        Screen.Settings -> SettingsScreen(navigateTo = { newScreen ->
                            currentScreen = newScreen
                        })
                    }
                }
            }
        }
    }
}