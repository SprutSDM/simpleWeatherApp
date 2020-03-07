package ru.zakoulov.weatherapp.data.source.accuweather

import ru.zakoulov.weatherapp.data.core.DataResult
import ru.zakoulov.weatherapp.data.models.DailyForecast
import ru.zakoulov.weatherapp.data.source.ForecastDataSource
import ru.zakoulov.weatherapp.data.source.accuweather.api.AwForecastApi
import ru.zakoulov.weatherapp.data.source.accuweather.mappers.AwFiveDayForecastMapper
import java.lang.Exception

class AwForecastDataSource(
    private val api: AwForecastApi,
    private val mapper: AwFiveDayForecastMapper
) : ForecastDataSource {

    override suspend fun getFiveDayForecast(cityId: Int): DataResult<DailyForecast> {
        val response = api.getFiveDayForecast(cityId)
        return if (response.isSuccessful) {
            DataResult.Success(mapper.map(response.body()!!))
        } else {
            DataResult.Fail(Exception(response.errorBody().toString()))
        }
    }
}
