package com.example.menuhomework.model.providers

import com.example.menuhomework.model.database.WeatherEntity
import kotlinx.coroutines.channels.ReceiveChannel

interface DataProvider {

    suspend fun subscribeToAllWeathers(): ReceiveChannel<List<WeatherEntity>>
    suspend fun getWeathersById(id: Long): WeatherEntity
    suspend fun saveWeathers(weather: WeatherEntity): WeatherEntity
    suspend fun deleteWeatherById(id: Long): List<WeatherEntity>
    suspend fun deleteAll(): List<WeatherEntity>
    suspend fun sortAllByName(isAsc: Int): List<WeatherEntity>
    suspend fun sortAllByDate(isAsc: Int): List<WeatherEntity>
}