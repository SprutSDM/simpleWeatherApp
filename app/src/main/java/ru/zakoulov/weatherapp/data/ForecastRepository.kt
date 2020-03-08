package ru.zakoulov.weatherapp.data

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.zakoulov.weatherapp.data.core.DataResult
import ru.zakoulov.weatherapp.data.models.City
import ru.zakoulov.weatherapp.data.models.DailyForecast
import ru.zakoulov.weatherapp.data.models.HourlyForecast
import ru.zakoulov.weatherapp.data.source.ForecastDataSource

class ForecastRepository(
    private val remoteDataSource: ForecastDataSource,
    private val coroutineScope: CoroutineScope,
    private val predefineCity: City
) {

    var currentCityId = MutableLiveData(predefineCity)
    val hourlyForecast = MutableLiveData<DataResult<HourlyForecast>>()
    val fiveDayForecast = MutableLiveData<DataResult<DailyForecast>>()

    fun getHourlyForecast() {
        coroutineScope.launch {
            val forecast = remoteDataSource.getHourlyForecast(currentCityId.value!!.id)
            hourlyForecast.postValue(forecast)
        }
    }

    fun getFiveDayForecast() {
        coroutineScope.launch {
            val forecast = remoteDataSource.getFiveDayForecast(currentCityId.value!!.id)
            fiveDayForecast.postValue(forecast)
        }
    }
}
