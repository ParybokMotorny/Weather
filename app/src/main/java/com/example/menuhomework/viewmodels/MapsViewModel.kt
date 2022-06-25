package com.example.menuhomework.viewmodels

import com.example.menuhomework.model.Repository
import com.example.menuhomework.model.database.Weather
import kotlinx.coroutines.launch

class MapsViewModel(private val repository: Repository) :
    BaseViewModel<Weather>() {

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