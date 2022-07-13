package com.example.menuhomework.viewmodels

import com.example.menuhomework.model.AppState
import com.example.menuhomework.model.Repository
import com.example.menuhomework.model.database.*
import com.example.menuhomework.model.exceptions.InvalidSortingException
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HistoryViewModel(
    private val repository: Repository
) : BaseViewModel<List<WeatherEntity>>() {

    private val weathersChannel by lazy { runBlocking { repository.getWeathers() } }

    init {
        launch {
            try {
                weathersChannel.consumeEach { result ->
                    setData(result)
                }
            } catch (e: Throwable) {
                setError(e)
            }
        }
    }

    fun deleteForId(id: Long) {
        launch {
            try {
                setData(repository.deleteWeatherById(id))
            } catch (e: Throwable) {
                setError(e)
            }
        }
    }

    fun deleteAll() {
        launch {
            try {
                setData(repository.deleteAll())
            } catch (e: Throwable) {
                setError(e)
            }
        }
    }

    fun sort(type: Int) {
        launch {
            try {
                when (type) {
                    DATE -> setData(repository.sortAllByDate(1))
                    DATEDESC -> setData(repository.sortAllByDate(2))
                    NAME -> setData(repository.sortAllByName(1))
                    NAMEDESC -> setData(repository.sortAllByName(2))
                    else -> {
                        setError(InvalidSortingException())
                    }
                }

            } catch (e: Throwable) {
                setError(e)
            }
        }
    }

    override fun onCleared() {
        weathersChannel.cancel()
    }
}