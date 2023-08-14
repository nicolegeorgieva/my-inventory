package com.example.inventory.screen

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.inventory.Screen
import com.example.inventory.data.BIG_WIPES_KEY
import com.example.inventory.data.BIG_WIPES_REQUIRED_COUNT
import com.example.inventory.data.KITCHEN_PAPER_KEY
import com.example.inventory.data.KITCHEN_PAPER_REQUIRED_COUNT
import com.example.inventory.data.NAME_KEY
import com.example.inventory.data.SMALL_WIPES_REQUIRED_SETS_COUNT
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
fun HomeScreen(
    navigateTo: (Screen) -> Unit
) {
    val context = LocalContext.current

    val name by remember {
        context.dataStore.data.map {
            it[NAME_KEY] ?: ""
        }
    }.collectAsState(initial = "")

    val smallWipesCount = readQuantity(key = SMALL_WIPES_SETS_KEY)
    val bigWipesCount = readQuantity(key = BIG_WIPES_KEY)
    val kitchenPaperCount = readQuantity(key = KITCHEN_PAPER_KEY)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Row {
            Text(
                text = "$name's Inventory",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = {
                    navigateTo(Screen.Settings)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Settings"
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        ItemRow(
            label = "Small wipes sets",
            itemCount = smallWipesCount,
            requiredCount = SMALL_WIPES_REQUIRED_SETS_COUNT,
            key = SMALL_WIPES_SETS_KEY
        )

        Spacer(modifier = Modifier.height(12.dp))

        ItemRow(
            label = "Big wipes",
            itemCount = bigWipesCount,
            requiredCount = BIG_WIPES_REQUIRED_COUNT,
            key = BIG_WIPES_KEY
        )

        Spacer(modifier = Modifier.height(12.dp))

        ItemRow(
            label = "Kitchen paper",
            itemCount = kitchenPaperCount,
            requiredCount = KITCHEN_PAPER_REQUIRED_COUNT,
            key = KITCHEN_PAPER_KEY
        )
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
fun OperationButtons(
    key: Preferences.Key<Int>,
    enabled: Boolean
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Button(
        onClick = {
            editQuantity(
                coroutineScope = coroutineScope,
                context = context,
                key = key,
                operation = QuantityChange.INCREMENT
            )
        }
    ) {
        Text(text = "+")
    }

    Spacer(modifier = Modifier.width(8.dp))

    Button(
        onClick = {
            editQuantity(
                coroutineScope = coroutineScope,
                context = context,
                key = key,
                operation = QuantityChange.DECREMENT
            )
        },
        enabled = enabled
    ) {
        Text(text = "-")
    }
}

@Composable
fun ItemRow(
    label: String,
    itemCount: Int,
    requiredCount: Int,
    key: Preferences.Key<Int>
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$label: $itemCount",
            color = if (itemCount < requiredCount)
                Color.Red else Color.Black
        )

        Spacer(modifier = Modifier.weight(1f))

        OperationButtons(
            key = key,
            enabled = itemCount > 0
        )
    }
}
