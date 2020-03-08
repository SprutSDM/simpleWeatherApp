package ru.zakoulov.weatherapp.data.models

import androidx.annotation.DrawableRes
import ru.zakoulov.weatherapp.R

data class Forecast(
    val hourlyForecast: HourlyForecast,
    val dailyForecasts: DailyForecast
)

data class DailyForecast(
    val date: Long,
    val dailyForecasts: List<ForecastInfo>
)

data class ForecastInfo(
    val date: Long,
    val minTemp: TempInfo,
    val maxTemp: TempInfo,
    val dayPreviewType: PreviewType
)

data class HourlyForecast(
    val date: Long,
    val temp: TempInfo,
    val realFeelTemp: TempInfo,
    val humidity: Int,
    val precipitation: Int,
    val previewType: PreviewType
)

data class TempInfo(
    val temp: Float,
    val unit: TempUnit
)

enum class TempUnit(val alias: String) {
    CENTIGRADE("°C"),
    FAHRENHEIT("°F"),
    OTHER("")
}

enum class PreviewType(@DrawableRes val previewSmall: Int, @DrawableRes val previewBig: Int) {
    SUN(R.drawable.ic_sun_small, R.drawable.ic_sun_big),
    MOON(R.drawable.ic_moon_small, R.drawable.ic_moon_big),
    CLOUD(R.drawable.ic_cloud_small, R.drawable.ic_cloud_big),
    SUN_CLOUD(R.drawable.ic_sun_cloudy_small, R.drawable.ic_sun_cloudy_big),
    MOON_CLOUD(R.drawable.ic_moon_cloudy_small, R.drawable.ic_moon_cloudy_big),
    RAIN(R.drawable.ic_rain_small, R.drawable.ic_rain_big),
    SHOW(R.drawable.ic_snow_small, R.drawable.ic_snow_big),
    THUNDER_STORM(R.drawable.ic_thunderstorm_small, R.drawable.ic_thunderstorm_big)
}
