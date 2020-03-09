package ru.zakoulov.weatherapp

import android.app.Activity
import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.zakoulov.weatherapp.data.ForecastRepository
import ru.zakoulov.weatherapp.data.models.City
import ru.zakoulov.weatherapp.data.source.ForecastDataSource
import ru.zakoulov.weatherapp.data.source.accuweather.AwForecastDataSource
import ru.zakoulov.weatherapp.data.source.accuweather.api.AwForecastApi
import ru.zakoulov.weatherapp.data.source.accuweather.mappers.AwCityMapper
import ru.zakoulov.weatherapp.data.source.accuweather.mappers.AwFiveDayForecastMapper
import ru.zakoulov.weatherapp.data.source.accuweather.mappers.AwHourlyForecastMapper

class App : Application() {

    private lateinit var job: Job

    lateinit var forecastRepository: ForecastRepository

    override fun onCreate() {
        super.onCreate()

        job = Job()
        val coroutineContext = job + Dispatchers.IO
        val scope = CoroutineScope(coroutineContext)

        val awApi = getAccuweatherApi()
        val awForecastDataSource: ForecastDataSource = AwForecastDataSource(
            api = awApi,
            hourlyForecastMapper = AwHourlyForecastMapper(),
            fiveDayForecastMapper = AwFiveDayForecastMapper(),
            cityMapper = AwCityMapper()
        )

        // Resources allows to predefine city for each localized region
        val predefineCity = City(
            id = getString(R.string.predefine_city_id),
            name = getString(R.string.predefine_city_name),
            countryName = getString(R.string.predefine_county_name)
        )

        forecastRepository = ForecastRepository(awForecastDataSource, scope, predefineCity)
    }

    private fun getAccuweatherApi(): AwForecastApi {
        val interceptor = Interceptor { chain ->
            val url = chain.request().url
                .newBuilder()
                .addQueryParameter("apikey", getString(R.string.api_accuweather_key))
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            chain.proceed(request)
        }

        val client = OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://dataservice.accuweather.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(AwForecastApi::class.java)
    }

    companion object {
        fun getApp(activity: Activity) = activity.application as App
    }
}
