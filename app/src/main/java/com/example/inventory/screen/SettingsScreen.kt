package com.example.inventory.screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import com.example.inventory.Screen
import com.example.inventory.data.NAME_KEY
import com.example.inventory.data.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navigateTo: (Screen) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val name by remember {
        context.dataStore.data.map {
            it[NAME_KEY] ?: ""
        }
    }.collectAsState(initial = "")

    BackHandler {
        navigateTo(Screen.Home)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Button(onClick = {
            navigateTo(Screen.Home)
        }) {
            Text(text = "<")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Set your name")

            Spacer(modifier = Modifier.weight(1f))

            TextField(
                value = name,
                onValueChange = { input ->
                    coroutineScope.launch {
                        withContext(Dispatchers.IO) {
                            context.dataStore.edit {
                                it[NAME_KEY] = input
                            }
                        }
                    }
                },
                label = { Text("Name") }
            )
        }
    }
}


