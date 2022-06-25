package com.example.menuhomework.viewmodels

import com.example.menuhomework.model.Repository
import com.example.menuhomework.model.Result
import com.example.menuhomework.model.database.Weather
import com.example.menuhomework.viewStates.AppState
import com.example.menuhomework.viewStates.CityViewState
import kotlinx.coroutines.launch

class CityViewModel(private val repository: Repository) :
    BaseViewModel<Weather>() {

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
