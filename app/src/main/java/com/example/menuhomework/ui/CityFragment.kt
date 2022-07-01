package com.example.menuhomework.ui

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.example.menuhomework.R
import com.example.menuhomework.databinding.FragmentCityBinding
import com.example.menuhomework.model.database.WeatherEntity
import com.example.menuhomework.viewmodels.CityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CityFragment :
    BaseFragment<WeatherEntity, FragmentCityBinding>(R.layout.fragment_city) {

    private var showError: Boolean = false
    override val viewModel: CityViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val refresh = binding.refresh
        refresh.setOnClickListener(clickListener)

        loadPreferences(requireActivity().getPreferences(MODE_PRIVATE))
    }

    override fun onDestroy() {
        super.onDestroy()

        savePreferences(requireActivity().getPreferences(MODE_PRIVATE))
    }

    private var clickListener: View.OnClickListener = View.OnClickListener {
        viewModel.loadWeather(binding.city.text.toString())
        binding.progressBar.isVisible = true
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
        binding.progressBar.isVisible = true
        showError = false
    }

    companion object {
        private const val CITY = "city"
    }

    override fun bindView() = FragmentCityBinding.bind(requireView())


    override fun renderSuccess(data: WeatherEntity) {
        binding.progressBar.isVisible = false

        val fragment = WeatherFragment.newInstance(data, false)

        val imm: InputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)

        childFragmentManager
            .beginTransaction()
            .replace(R.id.weather_container, fragment)
            .commit()
    }

    override fun renderError(error: Throwable) {
        binding.progressBar.isVisible = false

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
