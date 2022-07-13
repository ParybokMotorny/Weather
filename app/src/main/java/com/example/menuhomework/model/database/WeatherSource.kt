package com.example.menuhomework.model.database

import com.example.menuhomework.model.providers.DataProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class WeatherSource(private val dao: WeatherDao) : DataProvider {

    override suspend fun subscribeToAllWeathers(): ReceiveChannel<List<WeatherEntity>> =
        Channel<List<WeatherEntity>>(Channel.CONFLATED).apply {
            trySend(dao.getAllWeathers())
        }

    override suspend fun getWeathersById(id: Long): WeatherEntity =
        suspendCoroutine { continuation ->
            try {
                continuation.resume(dao.getWeatherById(id))
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
            }
        }


    override suspend fun saveWeathers(weather: WeatherEntity): WeatherEntity =
        suspendCoroutine { continuation ->
            try {
                dao.insertWeather(weather)
                continuation.resume(weather)
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
            }
        }


    override suspend fun deleteWeatherById(id: Long): List<WeatherEntity> =
        suspendCoroutine { continuation ->
            try {
                dao.deleteWeatherById(id)
                continuation.resume(dao.getAllWeathers())
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
            }
        }


    override suspend fun deleteAll(): List<WeatherEntity> =
        suspendCoroutine { continuation ->
            try {
                continuation.resume(listOf())
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
            }
        }


    override suspend fun sortAllByName(isAsc: Int): List<WeatherEntity> =
        suspendCoroutine { continuation ->
            try {
                continuation.resume(dao.getAllSortedByName(isAsc))
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
            }
        }


    override suspend fun sortAllByDate(isAsc: Int): List<WeatherEntity> =
        suspendCoroutine { continuation ->
            try {
                continuation.resume(dao.getAllSortedByDate(isAsc))
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
            }
        }
}

