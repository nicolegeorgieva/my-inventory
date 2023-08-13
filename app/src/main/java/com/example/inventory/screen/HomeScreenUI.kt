package com.example.inventory.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import com.example.inventory.data.SMALL_WIPES_KEY
import com.example.inventory.data.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomeScreenUI() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column {
        Text(text = "My Inventory")

        Spacer(modifier = Modifier.height(24.dp))

        Row {
            val smallWipesCount by remember {
                context.dataStore.data.map {
                    it[SMALL_WIPES_KEY] ?: 0
                }
            }.collectAsState(initial = 0)

            Text(text = "Small wipes: $smallWipesCount")

            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = {
                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        context.dataStore.edit {
                            it[SMALL_WIPES_KEY] = smallWipesCount + 1
                        }
                    }
                }
            }) {
                Text(text = "+")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        context.dataStore.edit {
                            it[SMALL_WIPES_KEY] = smallWipesCount - 1
                        }
                    }
                }
            }) {
                Text(text = "-")
            }
        }
    }
}