package com.example.inventory.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "myapp-data")

val SMALL_WIPES_KEY = intPreferencesKey("small_wipes")
const val SMALL_WIPES_REQUIRED_COUNT = 12