package com.example.menuhomework.viewmodels

import com.example.menuhomework.model.Repository
import com.example.menuhomework.model.database.WeatherEntity
import kotlinx.coroutines.launch

class CityViewModel(private val repository: Repository) :
    BaseViewModel<WeatherEntity>() {

    fun loadWeather(city: String) {
        launch {
            try {
                val weather = repository.requestWeather(city)
                setData(weather)
                repository.saveWeather(weather)
            } catch (e: Throwable) {
                setError(e)
            }
        }
    }
}
