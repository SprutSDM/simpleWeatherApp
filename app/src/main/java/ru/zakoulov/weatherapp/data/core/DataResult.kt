package ru.zakoulov.weatherapp.data.core

import java.lang.Exception

sealed class DataResult<out T: Any> {
    data class Success<out T: Any>(val data: T) : DataResult<T>()
    data class Fail(val exception: Exception) : DataResult<Nothing>()
    data class Loading(val quiet: Boolean) : DataResult<Nothing>()
}
