package com.example.menuhomework.model.providers

import com.example.menuhomework.model.Result
import com.example.menuhomework.model.database.Weather
import kotlinx.coroutines.channels.ReceiveChannel

interface DataProvider {

    suspend fun subscribeToAllWeathers(): ReceiveChannel<Result>
    suspend fun getWeathersById(id: Long): Weather
    suspend fun saveWeathers(weather: Weather): Weather
    suspend fun deleteWeatherById(id: Long): List<Weather>
    suspend fun deleteAll(): List<Weather>
    suspend fun sortAllByName(isAsc: Int): List<Weather>
    suspend fun sortAllByDate(isAsc: Int): List<Weather>
}