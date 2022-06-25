package com.example.menuhomework.model.database

import androidx.lifecycle.MutableLiveData
import com.example.menuhomework.model.Result
import com.example.menuhomework.model.providers.DataProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class WeatherSource(private val dao: WeatherDao) : DataProvider {

    var weathers: MutableLiveData<MutableList<Weather>> =
        MutableLiveData(dao.getAllWeathers().copy())

    override suspend fun subscribeToAllWeathers(): ReceiveChannel<Result> =
        Channel<Result>(Channel.CONFLATED).apply {
            try {
                trySend(Result.Success(dao.getAllWeathers()))
            } catch (e: Throwable) {
                trySend(Result.Error(e))
            }
        }


    private fun List<Weather>.copy(): MutableList<Weather> {
        val result = mutableListOf<Weather>()

        for (e in this) {
            result.add(e)
        }

        return result
    }

    override suspend fun getWeathersById(id: Long): Weather =
        suspendCoroutine { continuation ->
            try {
                continuation.resume(dao.getWeatherById(id))
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
            }
        }


    override suspend fun saveWeathers(weather: Weather): Weather =
        suspendCoroutine { continuation ->
            try {
                dao.insertWeather(weather)
                continuation.resume(weather)
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
            }
        }


    override suspend fun deleteWeatherById(id: Long): List<Weather> =
        suspendCoroutine { continuation ->
            try {
                continuation.resume(dao.getAllWeathers())
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
            }
        }


    override suspend fun deleteAll(): List<Weather> =
        suspendCoroutine { continuation ->
            try {
                continuation.resume(listOf())
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
            }
        }


    override suspend fun sortAllByName(isAsc: Int): List<Weather> =
        suspendCoroutine { continuation ->
            try {
                continuation.resume(dao.getAllSortedByName(isAsc))
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
            }
        }


    override suspend fun sortAllByDate(isAsc: Int): List<Weather> =
        suspendCoroutine { continuation ->
            try {
                continuation.resume(dao.getAllSortedByDate(isAsc))
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
            }
        }
}

