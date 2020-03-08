package ru.zakoulov.weatherapp.data.source.accuweather.mappers

import ru.zakoulov.weatherapp.data.models.PreviewType
import ru.zakoulov.weatherapp.data.models.TempUnit

abstract class AwBaseMapper {
    protected fun convertTempUnit(unitType: Int) = when (unitType) {
        17 -> TempUnit.CENTIGRADE
        18 -> TempUnit.FAHRENHEIT
        else -> TempUnit.OTHER
    }

    protected fun convertPreview(previewType: Int) = when (previewType) {
        in 1..5 -> PreviewType.SUN
        in 33..37 -> PreviewType.MOON
        6 -> PreviewType.SUN_CLOUD
        38 -> PreviewType.MOON_CLOUD
        in 7..11, 19 -> PreviewType.CLOUD
        in 12..14, 18, 26, 29, in 39..40 -> PreviewType.RAIN
        in 15..17, in 41..42 -> PreviewType.THUNDER_STORM
        in 20..25, in 43..44 -> PreviewType.SHOW
        else -> PreviewType.CLOUD
    }
}
