package com.example.menuhomework.model.retrofit

import com.example.menuhomework.model.retrofit.model.WeatherRequest
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


interface OpenWeatherRequests {
    @GET("data/2.5/weather")
    fun loadWeather(
        @Query("q") cityCountry: String?,
        @Query("appid") keyApi: String?
    ): Call<WeatherRequest>?

    @GET("data/2.5/weather")
    fun loadWeather(
        @Query("lat") latitude: Float?,
        @Query("lon") longitude: Float?,
        @Query("appid") keyApi: String?
    ): Call<WeatherRequest>?
}
