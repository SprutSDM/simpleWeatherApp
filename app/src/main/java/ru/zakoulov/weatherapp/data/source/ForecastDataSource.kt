package ru.zakoulov.weatherapp.data.source

import ru.zakoulov.weatherapp.data.core.DataResult
import ru.zakoulov.weatherapp.data.models.City
import ru.zakoulov.weatherapp.data.models.DailyForecast
import ru.zakoulov.weatherapp.data.models.HourlyForecast

interface ForecastDataSource {
    suspend fun getHourlyForecast(cityId: String): DataResult<HourlyForecast>

    suspend fun getFiveDayForecast(cityId: String): DataResult<DailyForecast>

    suspend fun searchCities(query: String): DataResult<List<City>>
}
