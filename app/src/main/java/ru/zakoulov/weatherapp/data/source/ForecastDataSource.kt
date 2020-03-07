package ru.zakoulov.weatherapp.data.source

import ru.zakoulov.weatherapp.data.core.DataResult
import ru.zakoulov.weatherapp.data.models.DailyForecast

interface ForecastDataSource {
    suspend fun getFiveDayForecast(cityId: Int): DataResult<DailyForecast>
}
