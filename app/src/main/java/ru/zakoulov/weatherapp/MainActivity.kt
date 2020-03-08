package ru.zakoulov.weatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.zakoulov.weatherapp.ui.mainforecast.MainForecastFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            navigateToMainForecast()
            return
        }
        App.getApp(this).forecastRepository.updateForecast()
    }

    fun navigateToMainForecast() = navigateTo(MainForecastFragment.INSTANCE, MainForecastFragment.TAG)

    private fun navigateTo(fragment: Fragment, tagFragment: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, tagFragment)
            .commit()
    }
}
