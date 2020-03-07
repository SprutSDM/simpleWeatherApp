package ru.zakoulov.weatherapp.data

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.zakoulov.weatherapp.data.core.DataResult
import ru.zakoulov.weatherapp.data.models.DailyForecast
import ru.zakoulov.weatherapp.data.source.ForecastDataSource

class ForecastRepository(
    private val remoteDataSource: ForecastDataSource,
    private val coroutineScope: CoroutineScope
) {

    var cityId = MutableLiveData<Int>()
    val fiveDayForecast = MutableLiveData<DataResult<DailyForecast>>()

    fun getFiveDayForecast(cityId: Int) {
        coroutineScope.launch {
            val forecast = remoteDataSource.getFiveDayForecast(cityId)
            fiveDayForecast.postValue(forecast)
        }
    }
}
