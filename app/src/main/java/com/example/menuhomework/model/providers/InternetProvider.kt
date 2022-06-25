package com.example.menuhomework.model.providers

import com.example.menuhomework.model.database.Weather

interface InternetProvider {

    suspend fun request(city: String): Weather
    suspend fun request(latitude: Float, longitude: Float): Weather
}