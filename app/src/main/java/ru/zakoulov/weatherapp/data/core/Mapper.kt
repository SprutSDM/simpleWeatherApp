package ru.zakoulov.weatherapp.data.core

interface Mapper<IN, OUT> {
    fun map(input: IN): OUT
}
