package ru.zakoulov.weatherapp.data.source.accuweather.mappers

import ru.zakoulov.weatherapp.data.core.Mapper
import ru.zakoulov.weatherapp.data.models.HourlyForecast
import ru.zakoulov.weatherapp.data.models.TempInfo
import ru.zakoulov.weatherapp.data.source.accuweather.responses.AwHourlyForecastResponse

class AwHourlyForecastMapper : Mapper<List<AwHourlyForecastResponse>, List<HourlyForecast>>, AwBaseMapper() {
    override fun map(input: List<AwHourlyForecastResponse>): List<HourlyForecast> {
        return input.map {
            HourlyForecast(
                date = it.date,
                temp = TempInfo(
                    temp = it.temp.value,
                    unit = convertTempUnit(it.temp.unitType)
                ),
                realFeelTemp = TempInfo(
                    temp = it.realFeelTemp.value,
                    unit = convertTempUnit(it.realFeelTemp.unitType)
                ),
                humidity = it.humidity,
                precipitation = it.precipitation,
                previewType = convertPreview(it.icon)
            )
        }
    }
}
