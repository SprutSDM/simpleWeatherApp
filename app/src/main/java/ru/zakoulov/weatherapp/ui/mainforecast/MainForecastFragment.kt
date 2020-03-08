package ru.zakoulov.weatherapp.ui.mainforecast

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.zakoulov.weatherapp.App
import ru.zakoulov.weatherapp.R
import ru.zakoulov.weatherapp.data.core.DataResult
import ru.zakoulov.weatherapp.data.models.DailyForecast
import ru.zakoulov.weatherapp.data.models.Forecast
import ru.zakoulov.weatherapp.data.models.HourlyForecast
import ru.zakoulov.weatherapp.utils.formatTemp
import java.text.SimpleDateFormat
import java.util.Locale

class MainForecastFragment : Fragment(), ForecastAdapterCallback {

    private lateinit var forecastContainer: View
    private lateinit var errorContainer: View
    private lateinit var loadingContainer: View
    private lateinit var butReload: Button

    private lateinit var todayTemp: TextView
    private lateinit var todayTempFeel: TextView
    private lateinit var todayPrecipitation: TextView
    private lateinit var todayHumidity: TextView
    private lateinit var todayForecastPreview: ImageView
    private lateinit var recyclerView: RecyclerView

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: DailyForecastViewAdapter

    private lateinit var errorWhileGettingData: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_forecast_main, container, false).apply {
            forecastContainer = findViewById(R.id.forecast_container)
            errorContainer = findViewById(R.id.error_containter)
            loadingContainer = findViewById(R.id.loading_container)

            forecastContainer.apply {
                todayTemp = findViewById(R.id.today_temp)
                todayTempFeel = findViewById(R.id.today_temp_feel)
                todayPrecipitation = findViewById(R.id.today_precipitation)
                todayHumidity = findViewById(R.id.today_humidity)
                todayForecastPreview = findViewById(R.id.today_forecast_preview)
                recyclerView = findViewById(R.id.recycler_view)
            }

            errorContainer.apply {
                butReload = findViewById(R.id.but_reload)
            }
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sdfDate = SimpleDateFormat(getString(R.string.sdf_date), Locale.US)
        viewManager = LinearLayoutManager(this.context)
        viewAdapter = DailyForecastViewAdapter(emptyList(), this, sdfDate)
        recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        errorWhileGettingData = getString(R.string.error_while_getting_data)
        App.getApp(requireActivity()).forecastRepository.forecast.observe(viewLifecycleOwner) {
            when (it) {
                is DataResult.Loading -> showLoading()
                is DataResult.Fail -> showError(it.exception.message ?: errorWhileGettingData)
                is DataResult.Success -> showLoaded(it.data)
            }
        }
    }

    private fun showLoading() {
        forecastContainer.visibility = View.GONE
        errorContainer.visibility = View.GONE
        loadingContainer.visibility = View.VISIBLE
    }

    private fun showLoaded(forecast: Forecast) {
        forecastContainer.visibility = View.VISIBLE
        errorContainer.visibility = View.GONE
        loadingContainer.visibility = View.GONE
        setTodayForecastInfo(forecast.hourlyForecast)
        setDailyForecastInfo(forecast.dailyForecasts)
    }

    private fun showError(message: String) {
        forecastContainer.visibility = View.GONE
        errorContainer.visibility = View.VISIBLE
        loadingContainer.visibility = View.GONE

        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setTodayForecastInfo(forecast: HourlyForecast) {
        todayTemp.text = formatTemp(forecast.temp.temp, forecast.temp.unit.alias)

        todayTempFeel.setText(
            Html.fromHtml(getString(R.string.real_feel_temp_html,
                formatTemp(forecast.realFeelTemp.temp, forecast.realFeelTemp.unit.alias))),
            TextView.BufferType.SPANNABLE)

        todayHumidity.setText(
            Html.fromHtml(getString(R.string.humidity_html,
                "${forecast.humidity}%")),
            TextView.BufferType.SPANNABLE)

        todayPrecipitation.setText(
            Html.fromHtml(getString(R.string.precipitation_html,
                "${forecast.precipitation}%")),
            TextView.BufferType.SPANNABLE)

        todayForecastPreview.setImageDrawable((requireActivity() as AppCompatActivity)
            .getDrawable(forecast.previewType.previewBig))
    }

    private fun setDailyForecastInfo(forecast: DailyForecast) {
        viewAdapter.forecastList = forecast.dailyForecasts
    }

    override fun getDrawable(@DrawableRes id: Int) = (requireActivity() as AppCompatActivity).getDrawable(id)

    companion object {
        const val TAG = "MainForecastFragment"

        val INSTANCE: MainForecastFragment by lazy { MainForecastFragment() }
    }
}
