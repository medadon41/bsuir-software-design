package com.example.unitconverter.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.unitconverter.utils.ConverterUtil
import com.example.unitconverter.utils.ConverterUtil.CONVERTER_TYPES
import java.math.BigDecimal

class ConverterScreenViewModel: ViewModel() {

    val converterTypes = CONVERTER_TYPES
    val result = mutableStateOf(BigDecimal.ZERO)

    fun getConverterValues(
        converterType: String
    ): List<String>{
        return ConverterUtil.getConverterValues(converterType = converterType)
    }

    fun calculateResult(
        converterType: String,
        convertFrom: String,
        convertTo: String,
        amount: BigDecimal = BigDecimal.ZERO
    ){
        if(convertTo == "" || convertFrom == "") {
            result.value =  BigDecimal.ZERO
            return
        }
        result.value = ConverterUtil.convert(
            converterType = converterType,
            convertFrom = convertFrom,
            convertTo = convertTo,
            amount = amount
        )
    }
}