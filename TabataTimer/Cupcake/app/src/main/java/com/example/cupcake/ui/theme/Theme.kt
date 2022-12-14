/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.cupcake.dataStore

private val DarkColorPalette = darkColors(
    primary = Pink400,
    primaryVariant = Pink950,
    onPrimary = Black,
    secondary = Purple400,
    secondaryVariant = Purple400,
    onSecondary = Black
)

private val LightColorPalette = lightColors(
    primary = Pink600,
    primaryVariant = Pink950,
    onPrimary = White,
    secondary = Purple400,
    secondaryVariant = Purple700,
    onSecondary = Black
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)

@Composable
fun CupcakeTheme(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val viewModel = remember { ThemeViewModel(context.dataStore) }
    val state = viewModel.state.observeAsState()
    val fontSize = viewModel.fontSize.observeAsState()
    val value = state.value ?: isSystemInDarkTheme()
    val fontSizeValue = fontSize.value

//    val colors = if (darkTheme) {
//        DarkColorPalette
//    } else {
//        LightColorPalette
//    }

    LaunchedEffect(viewModel) { viewModel.request() }

    DarkThemeValue.current.value = value
    MaterialTheme(
        colors = if (value) DarkColorPalette else LightColorPalette,
        typography = Typography(
            body1 = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = fontSizeValue?.toInt()?.sp ?: 16f.toInt().sp
            )),
        shapes = Shapes,
        content = content
    )
}

@Composable
@ReadOnlyComposable
fun isDarkTheme() = DarkThemeValue.current.value

@SuppressLint("CompositionLocalNaming")
private val DarkThemeValue = compositionLocalOf { mutableStateOf(false) }

@Composable
@ReadOnlyComposable
infix fun <T> T.orInLightTheme(other: T): T = if (isDarkTheme()) this else other

