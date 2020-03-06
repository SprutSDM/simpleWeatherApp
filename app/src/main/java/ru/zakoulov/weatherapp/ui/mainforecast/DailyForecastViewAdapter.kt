package ru.zakoulov.weatherapp.ui.mainforecast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.zakoulov.weatherapp.R
import ru.zakoulov.weatherapp.data.models.ForecastInfo
import ru.zakoulov.weatherapp.data.models.PreviewType
import ru.zakoulov.weatherapp.data.models.TempInfo
import ru.zakoulov.weatherapp.utils.formatTemp

class DailyForecastViewAdapter(
    forecastList: List<ForecastInfo>
) : RecyclerView.Adapter<DailyForecastViewAdapter.ForecastViewHolder>() {

    var forecastList: List<ForecastInfo> = forecastList
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val forecastItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.day_forecast_item, parent, false) as View
        return ForecastViewHolder(forecastItem)
    }

    override fun getItemCount() = forecastList.size

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecast = forecastList[position]
        holder.apply {
            setDate(forecast.date)
            setTemp(forecast.minTemp, forecast.maxTemp)

        }
    }

    class ForecastViewHolder(val forecast: View) : RecyclerView.ViewHolder(forecast) {
        private val date: TextView = forecast.findViewById(R.id.forecast_date)
        private val tempDay: TextView = forecast.findViewById(R.id.forecast_temp_day)
        private val tempNight: TextView = forecast.findViewById(R.id.forecast_temp_night)
        private val preview: ImageView = forecast.findViewById(R.id.forecast_preview)

        fun setDate(date: Long) {
            // TODO add formatting
        }

        fun setTemp(min: TempInfo, max: TempInfo) {
            tempDay.text = formatTemp(min.temp, min.unit.alias)
            tempNight.text = formatTemp(max.temp, max.unit.alias)
        }

        fun setPreview(previewType: PreviewType) {
            // TODO need drawables
        }
    }
}
