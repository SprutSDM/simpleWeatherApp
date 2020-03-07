package ru.zakoulov.weatherapp.data.source.accuweather.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.zakoulov.weatherapp.data.source.accuweather.responses.AwDailyForecastResponse

interface AwForecastApi {
    @GET("daily/5day/{cityId}")
    suspend fun getFiveDayForecast(@Path("cityId") cityId: Int) : Response<AwDailyForecastResponse>
}
