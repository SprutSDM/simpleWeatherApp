package ru.zakoulov.weatherapp.data.source.accuweather.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.zakoulov.weatherapp.data.source.accuweather.models.AwCity
import ru.zakoulov.weatherapp.data.source.accuweather.responses.AwDailyForecastResponse
import ru.zakoulov.weatherapp.data.source.accuweather.responses.AwHourlyForecastResponse

interface AwForecastApi {
    @GET("forecasts/v1/hourly/1hour/{cityId}")
    suspend fun getOneHourForecast(
        @Path("cityId") cityId: String,
        @Query("details") details: Boolean = true,
        @Query("metric") metric: Boolean = true
    ): Response<List<AwHourlyForecastResponse>>

    @GET("forecasts/v1/daily/5day/{cityId}")
    suspend fun getFiveDayForecast(
        @Path("cityId") cityId: String,
        @Query("metric") metric: Boolean = true
    ): Response<AwDailyForecastResponse>

    @GET("locations/v1/cities/search")
    suspend fun getCities(
        @Query("q") query: String
    ): Response<List<AwCity>>
}
