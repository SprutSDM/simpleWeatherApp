package ru.zakoulov.weatherapp.data.source.accuweather

import ru.zakoulov.weatherapp.data.core.DataResult
import ru.zakoulov.weatherapp.data.models.City
import ru.zakoulov.weatherapp.data.models.DailyForecast
import ru.zakoulov.weatherapp.data.models.HourlyForecast
import ru.zakoulov.weatherapp.data.source.ForecastDataSource
import ru.zakoulov.weatherapp.data.source.accuweather.api.AwForecastApi
import ru.zakoulov.weatherapp.data.source.accuweather.mappers.AwCityMapper
import ru.zakoulov.weatherapp.data.source.accuweather.mappers.AwFiveDayForecastMapper
import ru.zakoulov.weatherapp.data.source.accuweather.mappers.AwHourlyForecastMapper
import java.lang.Exception

class AwForecastDataSource(
    private val api: AwForecastApi,
    private val hourlyForecastMapper: AwHourlyForecastMapper,
    private val fiveDayForecastMapper: AwFiveDayForecastMapper,
    private val cityMapper: AwCityMapper
) : ForecastDataSource {

    override suspend fun getHourlyForecast(cityId: String): DataResult<HourlyForecast> {
        val response = api.getOneHourForecast(cityId)
        return if (response.isSuccessful) {
            DataResult.Success(hourlyForecastMapper.map(response.body()!!)[0])
        } else {
            DataResult.Fail(Exception(response.message()))
        }
    }

    override suspend fun getFiveDayForecast(cityId: String): DataResult<DailyForecast> {
        val response = api.getFiveDayForecast(cityId)
        return if (response.isSuccessful) {
            DataResult.Success(fiveDayForecastMapper.map(response.body()!!))
        } else {
            DataResult.Fail(Exception(response.message()))
        }
    }

    override suspend fun searchCities(query: String): DataResult<List<City>> {
        val response = api.getCities(query)
        return if (response.isSuccessful) {
            DataResult.Success(response.body()!!.map { cityMapper.map(it) })
        } else {
            DataResult.Fail(Exception(response.message()))
        }
    }
}
