package ru.zakoulov.weatherapp.ui.citypicker

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.zakoulov.weatherapp.App
import ru.zakoulov.weatherapp.R
import ru.zakoulov.weatherapp.data.ForecastRepository
import ru.zakoulov.weatherapp.data.core.DataResult
import ru.zakoulov.weatherapp.data.models.City

class CityPickerFragment : BottomSheetDialogFragment(), CityPickerCallback {

    private lateinit var butCloseCityPicker: View
    private lateinit var butSearch: View
    private lateinit var cityEditText: EditText
    private lateinit var recyclerView: RecyclerView

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: CitiesViewAdapter
    private lateinit var bsBehavior: BottomSheetBehavior<FrameLayout>

    private lateinit var forecastRepository: ForecastRepository

    override fun getTheme() = R.style.AppTheme_BottomSheetDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return inflater.inflate(R.layout.fragment_city_picker, container, false).apply {
            recyclerView = findViewById(R.id.recycler_view)
            butSearch = findViewById(R.id.but_search)
            cityEditText = findViewById(R.id.city_edit_text)
            cityEditText.requestFocus()
            butCloseCityPicker = findViewById(R.id.close_city_picker)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forecastRepository = App.getApp(requireActivity()).forecastRepository
        viewManager = LinearLayoutManager(this.context)
        forecastRepository.resetCities()
        viewAdapter = CitiesViewAdapter(emptyList(), forecastRepository.currentCity.value!!,this)
        recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        forecastRepository.cities.observe(viewLifecycleOwner) {
            when (it) {
                is DataResult.Success -> viewAdapter.cities = it.data
                is DataResult.Fail -> Unit
                is DataResult.Loading -> Unit
            }
        }

        cityEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchCities()
            } else {
                false
            }
        }
        butSearch.setOnClickListener {
            searchCities()
        }
        butCloseCityPicker.setOnClickListener {
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bsBehavior = bottomSheetDialog.behavior
        bsBehavior.skipCollapsed = true
        bottomSheetDialog.setOnShowListener {
            bsBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        return bottomSheetDialog
    }

    override fun pickCity(city: City) {
        forecastRepository.changeCity(city)
        dismiss()
    }

    private fun searchCities(): Boolean {
        val query = cityEditText.editableText.toString()
        if (query.isEmpty()) {
            Toast.makeText(requireContext(), R.string.empty_query, Toast.LENGTH_SHORT).show()
            return false
        }
        forecastRepository.searchCities(query)
        return true
    }
}
