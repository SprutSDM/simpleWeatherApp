package ru.zakoulov.weatherapp.data.models

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

enum class PreviewType {
    SUN,
    MOON,
    CLOUD,
    SUN_CLOUD,
    MOON_CLOUD,
    RAIN,
    SHOW,
    THUNDER_STORM,
}
