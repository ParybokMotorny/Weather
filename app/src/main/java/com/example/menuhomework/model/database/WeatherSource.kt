package com.example.menuhomework.model.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.menuhomework.model.providers.DataProvider
import com.example.menuhomework.model.database.dao.WeatherDao

class WeatherSource : DataProvider {
    private val dao: WeatherDao by lazy {
        App.instance.db.weatherDao
    }

    var weathers: MutableLiveData<MutableList<Weather>> =
        MutableLiveData(dao.getAllWeathers().copy())

    override fun subscribeToAllWeathers(): LiveData<MutableList<Weather>> {
        return weathers
    }

    private fun List<Weather>.copy(): MutableList<Weather> {
        val result = mutableListOf<Weather>()

        for (e in this) {
            result.add(e)
        }

        return result
    }

    override fun getWeathersById(id: Long): LiveData<Weather> =
        MutableLiveData(dao.getWeatherById(id))


    override fun saveWeathers(weather: Weather) {
        weathers.value?.add(dao.getWeatherById(dao.insertWeather(weather)))
    }

    override fun deleteWeatherById(id: Long) {
        dao.deleteWeatherById(id)
        weathers.value = dao.getAllWeathers().copy()
    }

    override fun deleteAll() {
        dao.deleteAll()
        weathers.value = dao.getAllWeathers().copy()
    }

    override fun sortAllByName(isAsc: Int) {
        weathers.value = dao.getAllSortedByName(isAsc).copy()
    }


    override fun sortAllByDate(isAsc: Int) {
        weathers.value = dao.getAllSortedByDate(isAsc).copy()
    }
}

