package com.example.cupcake.ui.theme

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ThemeViewModel(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {
    private val forceDarkModeKey = booleanPreferencesKey("theme")
    private val fontSizeKey = floatPreferencesKey("fontSize")

    val state = MutableLiveData<Boolean?>(null)
    val fontSize = MutableLiveData<Float>(16f)
    fun request() {
        viewModelScope.launch {
            dataStore.data.collectLatest {
                state.value = it[forceDarkModeKey]
                fontSize.value = it[fontSizeKey]
            }
        }
    }

    fun switchToUseSystemSettings(isSystemSettings: Boolean) {
        viewModelScope.launch {
            if (isSystemSettings) {
                dataStore.edit {
                    it.remove(forceDarkModeKey)
                }
            }
        }
    }

    fun switchToUseDarkMode(isDarkTheme: Boolean) {
        viewModelScope.launch {
            dataStore.edit {
                it[forceDarkModeKey] = isDarkTheme
            }
        }
    }

    fun changeFontSize(fontSize: Float) {
        viewModelScope.launch {
            dataStore.edit {
                it[fontSizeKey] = fontSize
            }
        }
    }
}