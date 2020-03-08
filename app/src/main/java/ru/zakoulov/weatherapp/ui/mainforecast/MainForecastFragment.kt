package ru.zakoulov.weatherapp.ui.mainforecast

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.zakoulov.weatherapp.App
import ru.zakoulov.weatherapp.R
import ru.zakoulov.weatherapp.data.models.HourlyForecast
import ru.zakoulov.weatherapp.utils.formatTemp

class MainForecastFragment : Fragment() {

    private lateinit var todayTemp: TextView
    private lateinit var todayTempFeel: TextView
    private lateinit var todayPrecipitation: TextView
    private lateinit var todayHumidity: TextView
    private lateinit var todayForecastPreview: ImageView
    private lateinit var recyclerView: RecyclerView

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: DailyForecastViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_forecast_main, container, false).apply {
            todayTemp = findViewById(R.id.today_temp)
            todayTempFeel = findViewById(R.id.today_temp_feel)
            todayPrecipitation = findViewById(R.id.today_precipitation)
            todayHumidity = findViewById(R.id.today_humidity)
            todayForecastPreview = findViewById(R.id.today_forecast_preview)
            recyclerView = findViewById(R.id.recycler_view)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewManager = LinearLayoutManager(this.context)
        viewAdapter = DailyForecastViewAdapter(emptyList())
        recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        App.getApp(requireActivity()).forecastRepository.forecast.observe(viewLifecycleOwner) {
            // TODO show loading and etc.
        }
    }

    private fun showLoading() {

    }

    private fun showLoaded() {

    }

    private fun showError() {

    }

    private fun setTodayForecastInfo(forecast: HourlyForecast) {
        todayTemp.text = formatTemp(forecast.temp.temp, forecast.temp.unit.alias)
        todayTempFeel.text = formatTemp(forecast.realFeelTemp.temp, forecast.realFeelTemp.unit.alias)
        // TODO format as html for colorized text
        todayHumidity.text = forecast.humidity.toString()
        todayPrecipitation.text = forecast.precipitation.toString()
        // todayForecastPreview.src = ...
    }

    companion object {
        const val TAG = "MainForecastFragment"

        val INSTANCE: MainForecastFragment by lazy { MainForecastFragment() }
    }
}
