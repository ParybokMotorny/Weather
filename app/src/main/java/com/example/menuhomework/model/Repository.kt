package com.example.menuhomework.model

import androidx.lifecycle.LiveData
import com.example.menuhomework.model.providers.DataProvider
import com.example.menuhomework.model.database.Weather
import com.example.menuhomework.model.database.WeatherSource
import com.example.menuhomework.model.database.dao.WeatherDao
import com.example.menuhomework.model.providers.InternetProvider
import com.example.menuhomework.model.retrofit.OpenWeatherMapProvider

object Repository {

    private var dataProvider: DataProvider = WeatherSource()
    private val internetProvider: InternetProvider = OpenWeatherMapProvider()

    fun initProvider(dao: WeatherDao) {
        //dataProvider = WeatherSource(dao)
    }

    fun getWeathers() = dataProvider.subscribeToAllWeathers()

    fun saveWeather(weather: Weather) = dataProvider.saveWeathers(weather)

    fun getWeatherById(id: Long) = dataProvider.getWeathersById(id)

    fun deleteWeatherById(id: Long){
        dataProvider.deleteWeatherById(id)
    }

    fun deleteAll(){
        dataProvider.deleteAll()
    }

    fun sortAllByName(isAsc: Int) = dataProvider.sortAllByName(isAsc)

    fun sortAllByDate(isAsc: Int) = dataProvider.sortAllByDate(isAsc)

    fun requestWeather(city: String): LiveData<RequestResult> =
        internetProvider.request(city)

    fun requestWeather(latitude: Float, longitude: Float): LiveData<RequestResult> =
        internetProvider.request(latitude, longitude)
}