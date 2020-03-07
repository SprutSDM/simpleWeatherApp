package ru.zakoulov.weatherapp.data.source.accuweather.responses

import com.google.gson.annotations.SerializedName
import ru.zakoulov.weatherapp.data.source.accuweather.models.AwForecast
import ru.zakoulov.weatherapp.data.source.accuweather.models.AwHeadline
import ru.zakoulov.weatherapp.data.source.accuweather.models.AwTemperature

data class AwDailyForecastResponse(
    @SerializedName("Headline") val headline: AwHeadline,
    @SerializedName("DailyForecasts") val forecasts: List<AwForecast>
)

data class AwHourlyForecaseResposne(
    @SerializedName("EpochDateTime") val date: Long,
    @SerializedName("WeatherIcon") val icon: Int,
    @SerializedName("Temperature") val temp: AwTemperature,
    @SerializedName("RealFeelTemperature") val realFeelTemp: AwTemperature,
    @SerializedName("RelativeHumidity") val humidity: Int,
    @SerializedName("PrecipitationProbability") val precipitation: Int
)
