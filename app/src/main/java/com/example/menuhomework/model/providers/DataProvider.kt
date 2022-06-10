package com.example.menuhomework.model.providers

import androidx.lifecycle.LiveData
import com.example.menuhomework.model.database.Weather

interface DataProvider {

    fun subscribeToAllWeathers(): LiveData<MutableList<Weather>>

    fun getWeathersById(id: Long): LiveData<Weather>

    fun saveWeathers(weather: Weather)

    fun deleteWeatherById(id: Long)

    fun deleteAll()

    fun sortAllByName(isAsc: Int)

    fun sortAllByDate(isAsc: Int)
}