package ru.zakoulov.weatherapp.data.source

import ru.zakoulov.weatherapp.data.core.DataResult
import ru.zakoulov.weatherapp.data.models.DailyForecast
import ru.zakoulov.weatherapp.data.models.HourlyForecast

interface ForecastDataSource {
    suspend fun getHourlyForecast(cityId: Int): DataResult<HourlyForecast>

    suspend fun getFiveDayForecast(cityId: Int): DataResult<DailyForecast>
}
