package com.example.menuhomework.viewmodels

import com.example.menuhomework.model.Repository
import com.example.menuhomework.model.database.WeatherEntity
import kotlinx.coroutines.launch

class MapsViewModel(private val repository: Repository) :
    BaseViewModel<WeatherEntity>() {

    fun loadWeather(latitude: Float, longitude: Float) {
        launch {
            try {
                val weather = repository.requestWeather(latitude, longitude)
                setData(weather)
                repository.saveWeather(weather)
            } catch (e: Throwable) {
                setError(e)
            }
        }
    }
}