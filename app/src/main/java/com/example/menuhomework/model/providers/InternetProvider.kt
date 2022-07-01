package com.example.menuhomework.model.providers

import com.example.menuhomework.model.database.WeatherEntity

interface InternetProvider {

    suspend fun request(city: String): WeatherEntity
    suspend fun request(latitude: Float, longitude: Float): WeatherEntity
}