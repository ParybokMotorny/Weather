package com.example.menuhomework.model.retrofit

import com.example.menuhomework.model.database.Weather
import com.example.menuhomework.model.exceptions.CityDoesNotExistException
import com.example.menuhomework.model.exceptions.InternetException
import com.example.menuhomework.model.retrofit.model.WeatherRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class Retrofit(
    private val onCompleted: (Weather) -> Unit,
    private val onFail: (Throwable) -> Unit
) {

    private var openWeatherRequests: OpenWeatherRequests? = null

    init {
        initRetrofit()
    }

    fun run(city: String, keyApi: String) {
        requestRetrofit(
            city,
            keyApi
        )
    }

    fun run(latitude: Float, longitude: Float, keyApi: String) {
        requestRetrofit(
            latitude,
            longitude,
            keyApi
        )
    }

    private fun initRetrofit() {
        openWeatherRequests = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherRequests::class.java)
    }

    private fun requestRetrofit(city: String, keyApi: String) {
        openWeatherRequests?.loadWeather(city, keyApi)
            ?.enqueue(callback)
    }

    private fun requestRetrofit(latitude: Float, longitude: Float, keyApi: String) {
        openWeatherRequests?.loadWeather(latitude, longitude, keyApi)
            ?.enqueue(callback)
    }

    private var callback = object : Callback<WeatherRequest?> {
        override fun onResponse(
            call: Call<WeatherRequest?>,
            response: Response<WeatherRequest?>
        ) {
            response.body()?.let {
                onCompleted(it.convertToRequest())
            } ?: run {
                onFail(CityDoesNotExistException())
            }
        }

        override fun onFailure(call: Call<WeatherRequest?>, t: Throwable) {
            onFail(InternetException())
        }
    }
}