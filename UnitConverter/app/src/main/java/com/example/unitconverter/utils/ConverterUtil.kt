package com.example.unitconverter.utils

import java.math.BigDecimal

object ConverterUtil {

    val CONVERTER_TYPES = listOf(
        "Weight",
        "Length",
        "Time",
        "Speed",
        "Data size"
    )

    private val weight = mapOf(
        "Tonne" to 1e-6,
        "Kilogram" to 0.001,
        "Gram" to 1.0,
        "Milligram" to 1000.0,
        "Microgram" to 1e+6,
        "Pound" to 0.00220462,
    )

    private val length = mapOf(
        "Kilometre" to 0.001,
        "Metre" to 1.0,
        "Centimetre" to 100.0,
        "Millimetre" to 1000.0,
        "Micrometre" to 1e+6,
        "Nanometre" to 1e+9,
        "Mile" to 0.000621371,
        "Inch" to 0.0254
    )

    private val time = mapOf(
        "Nanosecond" to 6e+10,
        "Microsecond" to 6e+7,
        "Millisecond" to 6e+4,
        "Second" to 60.0,
        "Minute" to 1.0,
        "Hour" to 0.0167,
        "Day" to 0.000694,
        "Week" to 9.9206e-5,
        "Month" to 2.2831e-5,
        "Year" to 1.9026e-6,
        "Decade" to 1.9026e-7,
        "Century" to 1.9026e-8,
    )

    private val speed = mapOf(
        "Miles per hour" to 1.0,
        "Foot per second" to 1.4667,
        "Metre per second" to 0.44704,
        "Kilometre per hour" to 1.60934
    )

    private val dataSize = mapOf(
        "Bit" to 8.0,
        "Kilobit" to 0.008,
        "Megabit" to 8e-6,
        "Gigabit" to 8e-9,
        "Terabit" to 8e-12,
        "Petabit" to 8e-15,
        "Byte" to 1.0,
        "Kilobyte" to 0.001,
        "Megabyte" to 1e-6,
        "Gigabyte" to 1e-9,
        "Terabyte" to 1e-12,
        "Petabyte" to 1e-15
    )

//    fun convert(
//        converterType: String,
//        convertFrom: String,
//        convertTo: String,
//        amount: Double
//    ):Double{
//        return when(converterType){
//            "Weight" -> amount * weight[convertTo]!! / weight[convertFrom]!!
//            "Length" -> amount * length[convertTo]!! / length[convertFrom]!!
//            "Time" -> amount * time[convertTo]!! / time[convertFrom]!!
//            "Speed" -> amount * speed[convertTo]!! / speed[convertFrom]!!
//            "Data size" -> amount * dataSize[convertTo]!! / dataSize[convertFrom]!!
//            else -> 0.0
//        }
//    }

    fun convert(
        converterType: String,
        convertFrom: String,
        convertTo: String,
        amount: BigDecimal
    ):BigDecimal{
        return when(converterType){
            "Weight" -> amount.multiply(BigDecimal.valueOf(weight[convertTo]!! / weight[convertFrom]!!))
            "Length" -> amount.multiply(BigDecimal.valueOf(length[convertTo]!! / length[convertFrom]!!))
            "Time" -> (amount.multiply(BigDecimal.valueOf(time[convertTo]!! / time[convertFrom]!!)))
            "Speed" -> amount.multiply(BigDecimal.valueOf(speed[convertTo]!! / speed[convertFrom]!!))
            "Data size" -> amount.multiply(BigDecimal.valueOf(dataSize[convertTo]!! / dataSize[convertFrom]!!))
            else -> BigDecimal.ZERO
        }
    }

    fun getConverterValues(
        converterType: String
    ): List<String> {
        return when (converterType) {
            "Weight" -> weight.keys
            "Length" -> length.keys
            "Time" -> time.keys
            "Speed" -> speed.keys
            "Data size" -> dataSize.keys
            else -> setOf()
        }.toList()
    }
}