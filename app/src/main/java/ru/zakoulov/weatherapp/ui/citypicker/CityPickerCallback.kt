package ru.zakoulov.weatherapp.ui.citypicker

import ru.zakoulov.weatherapp.data.models.City

interface CityPickerCallback {
    fun pickCity(city: City)
}
