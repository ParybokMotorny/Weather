package com.example.menuhomework.viewmodels

import com.example.menuhomework.model.Repository
import com.example.menuhomework.model.RequestResult
import com.example.menuhomework.model.database.App
import com.example.menuhomework.model.database.Weather
import com.example.menuhomework.viewStates.AppState
import com.example.menuhomework.viewStates.CityViewState

class CityViewModel(private val repository: Repository = Repository) :
    BaseViewModel<Weather, CityViewState>() {

    fun saveChanges(weather: Weather) {
        repository.saveWeather(weather)
    }

    fun loadNote(city: String) {
        repository.requestWeather(city).observeForever { requestResult ->
            if (requestResult == null) return@observeForever

            when (requestResult) {
                is RequestResult.Success<*> -> {
                    _liveData.value =
                        AppState.Success(CityViewState(requestResult.data as Weather))
                    repository.saveWeather(requestResult.data)
                }
                else -> _liveData.value =
                    AppState.Error((requestResult as RequestResult.Error).error)
            }
        }
    }
}
