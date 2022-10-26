package com.example.unitconverter.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.unitconverter.R

@Composable
fun ResultNumberField(value: String, onCopyClick: () -> Unit) {
    TextField(
        leadingIcon = { IconButton(onClick = onCopyClick) {
            Icon(painter = painterResource(id = R.drawable.ic_baseline_file_copy_24), contentDescription = "Copy")
        } },
        readOnly = true,
        value = value,
        onValueChange = { },
        label = { Text(stringResource(R.string.converted)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
    )
}