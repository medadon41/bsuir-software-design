package com.example.unitconverter.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverter.viewmodel.ConverterScreenViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unitconverter.R
import java.math.BigDecimal

@Composable
fun ConverterScreen(
    viewModel: ConverterScreenViewModel = viewModel()
) {
    var clipboardManager: ClipboardManager = LocalClipboardManager.current
    var converterTypeText by rememberSaveable { mutableStateOf("") }
    val converterTypes = viewModel.converterTypes
    val converterValues = viewModel.getConverterValues(converterTypeText)

    var selectedConvertedToType by rememberSaveable { mutableStateOf("") }
    var selectedConvertedFromType by rememberSaveable { mutableStateOf("") }

    var amountInput by rememberSaveable { mutableStateOf("") }


    var isFieldActive = selectedConvertedFromType != ""
    var isDropdownActive = converterTypeText != ""

    val amountValue = when(amountInput) {
        "" -> BigDecimal.ZERO
        "-" -> BigDecimal.ZERO
        "." -> BigDecimal.ZERO
        else -> BigDecimal(amountInput)
    }


    viewModel.calculateResult(
        converterType = converterTypeText,
        convertFrom = selectedConvertedFromType,
        convertTo = selectedConvertedToType,
        amount = amountValue
    )

    Column(
        modifier = Modifier
            .padding(32.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val toastContext = LocalContext.current
        Text(
            text = "Unit Converter",
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        DropDownMenu(
            suggestions = converterTypes,
            selectedText = converterTypeText,
            onValueChange = { converterTypeText = it },
            onItemSelected = { converterTypeText = it
                             selectedConvertedFromType = ""
                             selectedConvertedToType = ""
                             amountInput = ""},
            label = "Unit Category",
            isActive = true
        )

        Spacer(Modifier.height(16.dp))

        DropDownMenu(
            suggestions = converterValues,
            selectedText = selectedConvertedFromType,
            onValueChange = { selectedConvertedFromType = it },
            onItemSelected = { selectedConvertedFromType = it },
            label = "Convert From",
            isActive = isDropdownActive
        )

        EditNumberField(value = amountInput,
            onValueChange = {
                if (validateInput(it, toastContext)) {
                    amountInput = it
                    if (it == "") {
                        Toast.makeText(toastContext, "Text field is empty", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            onCopyClick = {
                clipboardManager.setText(AnnotatedString(amountInput))
                Toast.makeText(toastContext, "Value copied to clipboard", Toast.LENGTH_SHORT).show()
            },
            isActive = isFieldActive
        )

        IconButton(onClick = {
            var tempType = selectedConvertedFromType
            var tempResult = viewModel.result.value.toPlainString()
            selectedConvertedFromType = selectedConvertedToType
            selectedConvertedToType = tempType
            amountInput = tempResult
            },
            enabled = isFieldActive,
            modifier = Modifier.align(Alignment.CenterHorizontally))
            {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_swap_vert_24),
                contentDescription = "Swap"
            )
        }

        DropDownMenu(
            suggestions = converterValues,
            selectedText = selectedConvertedToType,
            onValueChange = { selectedConvertedToType = it },
            onItemSelected = { selectedConvertedToType = it },
            label = "Convert To",
            isActive = isDropdownActive
        )

        ResultNumberField(
            value = viewModel.result.value.toPlainString(),
            onCopyClick = {
                clipboardManager.setText(AnnotatedString(viewModel.result.value.toString()))
                Toast.makeText(toastContext, "Value copied to clipboard", Toast.LENGTH_SHORT).show()
            }
        )

        if(converterTypeText == "Length") {
            Spacer(Modifier.height(16.dp))
            EasterEgg()
        }
    }
}

fun validateInput(input: String, context: Context) : Boolean {
    if (input.contains("E-") || input.contains("E+")) {
        Toast.makeText(context, "Scientific shit is not supported", Toast.LENGTH_SHORT).show()
        return false
    }
    if ((input.filter { it -> it == '.' }).length > 1) {
        Toast.makeText(context, "Two dots are not supported", Toast.LENGTH_SHORT).show()
        return false
    }
    if (input.contains("-")) {
        Toast.makeText(context, "Minuses are not supported", Toast.LENGTH_SHORT).show()
        return false
    }
    if ((input.filter { it -> (it != '.' && !it.isDigit())}).isNotEmpty()) {
        Toast.makeText(context, "Only digits and dot are allowed", Toast.LENGTH_SHORT).show()
        return false
    }
    return true
}