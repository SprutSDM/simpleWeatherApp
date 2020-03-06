package ru.zakoulov.weatherapp.utils

fun formatTemp(temp: Float, unit: String): String {
    return "${if (temp < 0) "" else "+"}$temp $unit"
}
