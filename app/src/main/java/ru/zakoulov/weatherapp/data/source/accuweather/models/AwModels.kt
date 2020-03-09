package ru.zakoulov.weatherapp.data.source.accuweather.models

import com.google.gson.annotations.SerializedName

data class AwHeadline(
    @SerializedName("EndEpochDate") val date: Long
)

data class AwForecast(
    @SerializedName("EpochDate") val date: Long,
    @SerializedName("Temperature") val temp: AwTemperatureInfo,
    @SerializedName("Day") val day: AwDayInfo
)

data class AwTemperatureInfo(
    @SerializedName("Minimum") val minimum: AwTemperature,
    @SerializedName("Maximum") val maximum: AwTemperature
)

data class AwTemperature(
    @SerializedName("Value") val value: Float,
    @SerializedName("UnitType") val unitType: Int
)

data class AwDayInfo(
    @SerializedName("Icon") val icon: Int
)

data class AwCity(
    @SerializedName("Key") val id: String,
    @SerializedName("LocalizedName") val name: String,
    @SerializedName("Country") val country: AwCountryInfo
)

data class AwCountryInfo(
    @SerializedName("LocalizedName") val name: String
)
