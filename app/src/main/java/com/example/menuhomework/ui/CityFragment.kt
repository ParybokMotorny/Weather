package com.example.menuhomework.ui

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.menuhomework.R
import com.example.menuhomework.databinding.FragmentCityBinding
import com.example.menuhomework.model.database.Weather
import com.example.menuhomework.viewStates.CityViewState
import com.example.menuhomework.viewmodels.CityViewModel

class CityFragment :
    BaseFragment<Weather, FragmentCityBinding, CityViewState, CityViewModel>(R.layout.fragment_city) {

    private var showError: Boolean = false
    override val viewModel: CityViewModel by lazy {
        ViewModelProvider(this).get(CityViewModel::class.java)
    }

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
        viewModel.loadNote(binding.city.text.toString())
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
        viewModel.loadNote(binding.city.text.toString())
        showError = false
    }

    companion object {
        private const val CITY = "city"
    }

    override fun bindView() = FragmentCityBinding.bind(requireView())


    override fun renderSuccess(data: CityViewState) {
        val fragment = WeatherFragment.newInstance(data.data)

        val imm: InputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)

        childFragmentManager
            .beginTransaction()
            .replace(R.id.weather_container, fragment)
            .commit()
        //viewModel.saveChanges(data.copyWeather())
    }

    override fun renderError(error: String) {

        if (showError) {
            AlertDialog.Builder(requireContext())
                .setTitle(error)
                .setCancelable(false)
                .setPositiveButton("OK")
                { _, _ -> }
                .create()
                .show()
        } else showError = true
    }
}
