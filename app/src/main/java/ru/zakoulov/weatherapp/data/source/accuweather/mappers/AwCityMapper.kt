package ru.zakoulov.weatherapp.data.source.accuweather.mappers

import ru.zakoulov.weatherapp.data.core.Mapper
import ru.zakoulov.weatherapp.data.models.City
import ru.zakoulov.weatherapp.data.source.accuweather.models.AwCity

class AwCityMapper : Mapper<AwCity, City> {
    override fun map(input: AwCity): City {
        return City(
            id = input.id,
            name = input.name,
            countryName = input.country.name
        )
    }
}
