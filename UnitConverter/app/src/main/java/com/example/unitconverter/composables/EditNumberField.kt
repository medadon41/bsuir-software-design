package com.example.unitconverter.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.unitconverter.R

@Composable
fun EditNumberField(value: String, onValueChange: (String) -> Unit, onCopyClick: () -> Unit, isActive: Boolean) {
    TextField(
        value = value,
        leadingIcon = { IconButton(
            onClick = onCopyClick,
            enabled = isActive) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_file_copy_24), contentDescription = "Copy")
            }
        },
        enabled = isActive,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.converted)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
}