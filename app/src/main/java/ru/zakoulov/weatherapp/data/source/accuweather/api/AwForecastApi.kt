package ru.zakoulov.weatherapp.data.source.accuweather.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.zakoulov.weatherapp.data.source.accuweather.responses.AwDailyForecastResponse
import ru.zakoulov.weatherapp.data.source.accuweather.responses.AwHourlyForecastResponse

interface AwForecastApi {
    @GET("hourly/1hour/{cityId}")
    suspend fun getOneHourForecast(@Path("cityId") cityId: Int,
                                   @Query("details") details: Boolean = true,
                                   @Query("metric") metric: Boolean = true
    ): Response<List<AwHourlyForecastResponse>>

    @GET("daily/5day/{cityId}")
    suspend fun getFiveDayForecast(@Path("cityId") cityId: Int,
                                   @Query("metric") metric: Boolean = true
    ): Response<AwDailyForecastResponse>
}
