package com.example.inventory.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "myapp-data")

val SMALL_WIPES_SETS_KEY = intPreferencesKey("small_wipes_sets")
const val SMALL_WIPES_REQUIRED_SETS_COUNT = 12

val BIG_WIPES_KEY = intPreferencesKey("big_wipes")
const val BIG_WIPES_REQUIRED_COUNT = 4

val KITCHEN_PAPER_KEY = intPreferencesKey("kitchen_paper")
const val KITCHEN_PAPER_REQUIRED_COUNT = 4