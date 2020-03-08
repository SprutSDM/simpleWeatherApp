package ru.zakoulov.weatherapp.data.source.accuweather.mappers

import ru.zakoulov.weatherapp.data.core.Mapper
import ru.zakoulov.weatherapp.data.models.DailyForecast
import ru.zakoulov.weatherapp.data.models.ForecastInfo
import ru.zakoulov.weatherapp.data.models.PreviewType
import ru.zakoulov.weatherapp.data.models.TempInfo
import ru.zakoulov.weatherapp.data.models.TempUnit
import ru.zakoulov.weatherapp.data.source.accuweather.responses.AwDailyForecastResponse

class AwFiveDayForecastMapper : Mapper<AwDailyForecastResponse, DailyForecast>, AwBaseMapper() {
    override fun map(input: AwDailyForecastResponse): DailyForecast {
        return DailyForecast(
            date = input.headline.date,
            dailyForecasts = input.forecasts.map {
                ForecastInfo(
                    date = it.date,
                    minTemp = TempInfo(
                        temp = it.temp.minimum.value,
                        unit = convertTempUnit(it.temp.minimum.unitType)),
                    maxTemp = TempInfo(
                        temp = it.temp.maximum.value,
                        unit = convertTempUnit(it.temp.maximum.unitType)),
                    dayPreviewType = convertPreview(it.day.icon)
                )
            }
        )
    }
}
