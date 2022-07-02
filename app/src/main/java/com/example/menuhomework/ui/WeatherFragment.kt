package com.example.menuhomework.ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.example.menuhomework.model.database.WeatherEntity
import com.example.menuhomework.databinding.FragmentWeatherBinding
import com.squareup.picasso.Picasso

class WeatherFragment : Fragment() {

    var item: WeatherEntity? = null
    private var binding: FragmentWeatherBinding? = null
    private var isShownDate: Boolean = true

    val args: WeatherFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isShownDate = args.isShowDate
        displayWeather(args.weather)
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }

    private fun displayWeather(weather: WeatherEntity) {
        binding?.textTemperature?.setText(
            String.format(
                "%.2f",
                (weather.temp + ABSOLUTE_ZERO)
            )
        )
        binding?.textPressure?.setText(weather.pressure.toString())
        binding?.textHumidity?.setText(weather.humidity.toString())
        binding?.cloudsEditText?.setText(weather.allClouds.toString())
        binding?.speedEditText?.setText(weather.windSpeed.toString())
        binding?.degEditText?.setText(weather.windDeg.toString())
        binding?.visibilityEditText?.setText(weather.visibility.toString())

        if (!isShownDate)
            binding?.rowDate?.isVisible = false
        else
            binding?.dateEditText?.setText(weather.date.toString())

        val uri = Uri.parse("http://openweathermap.org/img/w/${weather.icon}.png")

        Picasso.get()
            .load(uri)
            .into(binding?.imageView)
    }

    companion object {
        private const val ABSOLUTE_ZERO = -273.15f
    }
}