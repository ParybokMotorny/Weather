package com.example.menuhomework.viewmodels

import com.example.menuhomework.model.Repository
import com.example.menuhomework.model.RequestResult
import com.example.menuhomework.model.database.Weather
import com.example.menuhomework.viewStates.AppState
import com.example.menuhomework.viewStates.CityViewState
import com.example.menuhomework.viewStates.MapsViewState

class MapsViewModel(private val repository: Repository = Repository) :
    BaseViewModel<Weather, MapsViewState>() {

    fun saveChanges(weather: Weather) {
        repository.saveWeather(weather)
    }

    fun loadNote(latitude: Float, longitude: Float) {
        repository.requestWeather(latitude, longitude).observeForever { requestResult ->
            if (requestResult == null) return@observeForever

            when (requestResult) {
                is RequestResult.Success<*> -> {
                    _liveData.value =
                        AppState.Success(MapsViewState(requestResult.data as Weather))
                    repository.saveWeather(requestResult.data)
                }
                else -> _liveData.value =
                    AppState.Error((requestResult as RequestResult.Error).error)
            }
        }
    }
}