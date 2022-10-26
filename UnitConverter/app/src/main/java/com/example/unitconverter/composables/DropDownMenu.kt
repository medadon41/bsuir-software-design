package com.example.unitconverter.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize

@Composable
fun DropDownMenu(
    suggestions: List<String>,
    selectedText: String,
    onValueChange: (String) -> Unit,
    onItemSelected: (String) -> Unit,
    label: String,
    isActive: Boolean) {
    var expanded by remember { mutableStateOf(false) }

    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column {
        OutlinedTextField(
            readOnly = true,
            enabled = false,
            value = selectedText,
            onValueChange = onValueChange,
            modifier = Modifier
                .clickable(onClick = { expanded = !expanded })
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textfieldSize = coordinates.size.toSize()
                },
            label = { Text(label) },
            trailingIcon = {
                IconButton(
                    onClick = { expanded = !expanded },
                    enabled = isActive
                ) {
                    Icon(imageVector = Icons.Filled.KeyboardArrowDown, "dropdownArrow")
                }
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){textfieldSize.width.toDp()})
        ) {
            suggestions.forEachIndexed { index, name ->
                DropdownMenuItem(onClick = {
                    onItemSelected(suggestions[index])
                    expanded = false
                }) {
                    Text(text = name)
                }
            }
        }
    }

}