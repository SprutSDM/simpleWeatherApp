package ru.zakoulov.weatherapp.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.zakoulov.weatherapp.BuildConfig
import ru.zakoulov.weatherapp.data.core.DataResult
import ru.zakoulov.weatherapp.data.models.City
import ru.zakoulov.weatherapp.data.models.DailyForecast
import ru.zakoulov.weatherapp.data.models.Forecast
import ru.zakoulov.weatherapp.data.models.HourlyForecast
import ru.zakoulov.weatherapp.data.source.ForecastDataSource
import java.lang.Exception

class ForecastRepository(
    private val remoteDataSource: ForecastDataSource,
    private val coroutineScope: CoroutineScope,
    private val predefineCity: City
) {

    var currentCityId = MutableLiveData(predefineCity)
    val forecast = MutableLiveData<DataResult<Forecast>>()

    private fun getHourlyForecast() = coroutineScope.async {
        remoteDataSource.getHourlyForecast(currentCityId.value!!.id)
    }

    private fun getFiveDayForecast() = coroutineScope.async {
        remoteDataSource.getFiveDayForecast(currentCityId.value!!.id)
    }

    fun updateForecast() {
        forecast.value = DataResult.Loading()

        coroutineScope.launch {
            val hourlyForecastDeferred = getHourlyForecast()
            val fiveDayForecastDeferred = getFiveDayForecast()

            val hourlyForecast = hourlyForecastDeferred.await()
            val fiveDayForecast = fiveDayForecastDeferred.await()

            forecast.postValue(
                if (hourlyForecast.isSuccess() && fiveDayForecast.isSuccess()) {
                    DataResult.Success(Forecast(
                        hourlyForecast = (hourlyForecast as DataResult.Success).data,
                        dailyForecasts = (fiveDayForecast as DataResult.Success).data
                    ))
                } else if (hourlyForecast.isFail()) {
                    DataResult.Fail((hourlyForecast as DataResult.Fail).exception)
                } else if (fiveDayForecast.isFail()) {
                    DataResult.Fail((fiveDayForecast as DataResult.Fail).exception)
                } else {
                    // Only if result is still loading. How come?
                    DataResult.Fail(Exception("Unknown error"))
                })
        }
    }
}
