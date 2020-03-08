package ru.zakoulov.weatherapp.ui.mainforecast

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes

interface ForecastAdapterCallback {
    fun getDrawable(@DrawableRes id: Int) : Drawable?
}
