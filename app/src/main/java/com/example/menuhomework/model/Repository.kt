package com.example.menuhomework.model

import com.example.menuhomework.model.providers.DataProvider
import com.example.menuhomework.model.database.WeatherEntity
import com.example.menuhomework.model.providers.InternetProvider

class Repository(
    private val internetProvider: InternetProvider,
    private var dataProvider: DataProvider
) {
    suspend fun getWeathers() = dataProvider.subscribeToAllWeathers()
    suspend fun saveWeather(weather: WeatherEntity) = dataProvider.saveWeathers(weather)
    suspend fun getWeatherById(id: Long) = dataProvider.getWeathersById(id)
    suspend fun deleteWeatherById(id: Long) = dataProvider.deleteWeatherById(id)
    suspend fun deleteAll() = dataProvider.deleteAll()
    suspend fun sortAllByName(isAsc: Int): List<WeatherEntity> = dataProvider.sortAllByName(isAsc)
    suspend fun sortAllByDate(isAsc: Int) = dataProvider.sortAllByDate(isAsc)
    suspend fun requestWeather(city: String) = internetProvider.request(city)
    suspend fun requestWeather(latitude: Float, longitude: Float) =
        internetProvider.request(latitude, longitude)
}