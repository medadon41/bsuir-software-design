package com.example.cupcake.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cupcake.R
import com.example.cupcake.dataStore
import com.example.cupcake.ui.theme.ThemeViewModel

@Composable
fun SettingsScreen(
    onDeleteAllClicked: () -> Unit = {}
) {
    val context = LocalContext.current

    val viewModel = remember {
        ThemeViewModel(context.dataStore)
    }
    val value = viewModel.state.observeAsState().value
    val fontSizeState = viewModel.fontSize.observeAsState().value
    val systemInDarkTheme = isSystemInDarkTheme()

    val darkModeChecked by remember(value) {
        val checked = when (value) {
            null -> systemInDarkTheme
            else -> value
        }
        mutableStateOf(checked)
    }

    val fontSizeValue by remember(fontSizeState) {
        mutableStateOf(fontSizeState)
    }

    var sliderPosition by remember{mutableStateOf(16f)}

    val useDeviceModeChecked by remember(value) {
        val checked = when (value) {
            null -> true
            else -> false
        }
        mutableStateOf(checked)
    }

    LaunchedEffect(viewModel) {
        viewModel.request()
    }

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp))
    {
        Text(text = "Settings", style = MaterialTheme.typography.h4)
        Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Dark Mode")
            Switch(
                checked = darkModeChecked,
                onCheckedChange = { viewModel.switchToUseDarkMode(it) }
            )
        }
        Column() {
            // TODO: font size slider
            Text(text = "Font size")
            Slider(
                value = fontSizeValue ?: 16f,
                valueRange = 16f..32f,
                onValueChange = { viewModel.changeFontSize(it) }
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_delete_24),
                    contentDescription = null)
                Text(text = "Delete all timers")
            }
        }
        // TODO: language switch
    }
}

@Preview
@Composable
fun SettingsPreview() {
    SettingsScreen()
}