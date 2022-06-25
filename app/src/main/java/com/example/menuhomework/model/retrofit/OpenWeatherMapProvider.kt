package com.example.menuhomework.model.retrofit

import com.example.menuhomework.model.providers.InternetProvider
import com.example.menuhomework.model.database.Weather
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class OpenWeatherMapProvider : InternetProvider {

    override suspend fun request(city: String): Weather =
        suspendCoroutine { continuation ->
            Retrofit({ request ->
                continuation.resume(request)
            }, { exception ->
                continuation.resumeWithException(exception)
            }).run(city, key)
        }

    override suspend fun request(latitude: Float, longitude: Float): Weather =
        suspendCoroutine { continuation ->


            Retrofit({ request ->
                continuation.resume(request)
            }, { exception ->
                continuation.resumeWithException(exception)
            }).run(latitude, longitude, key)
        }

    companion object {
        private const val key = "6b0423304b20ad534ccceecc6d3c729a"
    }
}