package com.example.inventory.screen

import android.content.Context
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
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.inventory.data.SMALL_WIPES_SETS_KEY
import com.example.inventory.data.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class QuantityChange {
    INCREMENT, DECREMENT
}

@Composable
fun HomeScreenUI() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val smallWipesCount = readQuantity(key = SMALL_WIPES_SETS_KEY)

    Column {
        Text(text = "My Inventory")

        Spacer(modifier = Modifier.height(24.dp))

        Row {
            Text(text = "Small wipes sets: $smallWipesCount")

            Spacer(modifier = Modifier.weight(1f))

            OperationButtons(
                coroutineScope = coroutineScope,
                context = context,
                enabled = smallWipesCount > 0
            )
        }
    }
}

@Composable
fun readQuantity(key: Preferences.Key<Int>): Int {
    val context = LocalContext.current

    val count by remember {
        context.dataStore.data.map {
            it[key] ?: 0
        }
    }.collectAsState(initial = 0)

    return count
}

fun editQuantity(
    coroutineScope: CoroutineScope,
    context: Context,
    key: Preferences.Key<Int>,
    operation: QuantityChange
) {
    coroutineScope.launch {
        withContext(Dispatchers.IO) {
            context.dataStore.edit {
                it[key] = when (operation) {
                    QuantityChange.INCREMENT -> (it[key] ?: 0) + 1
                    QuantityChange.DECREMENT -> (it[key] ?: 0) - 1
                }
            }
        }
    }
}

@Composable
fun OperationButtons(coroutineScope: CoroutineScope, context: Context, enabled: Boolean) {
    Button(onClick = {
        editQuantity(
            coroutineScope = coroutineScope,
            context = context,
            key = SMALL_WIPES_SETS_KEY,
            operation = QuantityChange.INCREMENT
        )
    }) {
        Text(text = "+")
    }

    Spacer(modifier = Modifier.width(8.dp))

    Button(
        onClick = {
            editQuantity(
                coroutineScope = coroutineScope,
                context = context,
                key = SMALL_WIPES_SETS_KEY,
                operation = QuantityChange.DECREMENT
            )
        },
        enabled = enabled
    ) {
        Text(text = "-")
    }
}