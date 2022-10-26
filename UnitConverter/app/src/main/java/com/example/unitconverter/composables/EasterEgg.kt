package com.example.unitconverter.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import com.example.unitconverter.R

@Composable
fun EasterEgg() {
    val uriHandler = LocalUriHandler.current
    Row {
        Icon(painter = painterResource(id = R.drawable.ic_baseline_info_24), contentDescription = "Info")
        Text(
            text = "POV: You are american",
            modifier = Modifier.clickable( onClick =
            {
                uriHandler.openUri("https://www.youtube.com/watch?v=Y-3IV11_ZgA")
            }))
    }
}