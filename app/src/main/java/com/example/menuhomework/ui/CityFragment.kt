package com.example.menuhomework.ui

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import com.example.menuhomework.R
import com.example.menuhomework.databinding.FragmentCityBinding
import com.example.menuhomework.model.database.Weather
import com.example.menuhomework.viewStates.CityViewState
import com.example.menuhomework.viewmodels.CityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CityFragment :
    BaseFragment<Weather, FragmentCityBinding>(R.layout.fragment_city) {

    private var showError: Boolean = false
    override val viewModel: CityViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val refresh = binding.refresh
        refresh.setOnClickListener(clickListener)

        loadPreferences(requireActivity().getPreferences(MODE_PRIVATE))
    }

    override fun onDestroyView() {
        super.onDestroyView()

        savePreferences(requireActivity().getPreferences(MODE_PRIVATE))
//        binding = null
    }

    private var clickListener: View.OnClickListener = View.OnClickListener {
        viewModel.loadWeather(binding.city.text.toString())
    }

    private fun savePreferences(sharedPref: SharedPreferences) {
        val editor = sharedPref.edit()

        editor.putString(CITY, binding.city.text.toString())
        showError = true

        editor.apply()
    }

    private fun loadPreferences(sharedPref: SharedPreferences) {

        val city = sharedPref.getString(CITY, null)
        binding.city.setText(city)
        viewModel.loadWeather(binding.city.text.toString())
        showError = false
    }

    companion object {
        private const val CITY = "city"
    }

    override fun bindView() = FragmentCityBinding.bind(requireView())


    override fun renderSuccess(data: Weather) {
        val fragment = WeatherFragment.newInstance(data)

        val imm: InputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)

        childFragmentManager
            .beginTransaction()
            .replace(R.id.weather_container, fragment)
            .commit()
        //viewModel.saveChanges(data.copyWeather())
    }

    override fun renderError(error: Throwable) {

        if (showError) {
            AlertDialog.Builder(requireContext())
                .setTitle(error.message)
                .setCancelable(false)
                .setPositiveButton("OK")
                { _, _ -> }
                .create()
                .show()
        } else showError = true
    }
}
