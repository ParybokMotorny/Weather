package com.example.menuhomework.model.retrofit.model

import com.example.menuhomework.model.database.WeatherEntity
import java.io.Serializable
import java.util.*

class WeatherRequest : Serializable {

    var coord = Coord()

    var weather: Array<Weather> = emptyArray()

    var base: String? = null

    var main = Main()

    var visibility = 0

    var wind = Wind()

    var clouds = Clouds()

    var dt: Long = 0

    var sys: Sys = Sys()

    var id: Long = 0

    var name: String = ""

    var cod = 0

    fun convertToRequest(): WeatherEntity {
        return WeatherEntity(
            date = Date(),
            city = name,
            humidity = main.humidity,
            pressure = main.pressure,
            temp = (main.temp * 10).toInt().toFloat() / 10,
            icon = weather[0].icon,
            allClouds = clouds.all,
            windDeg = wind.deg,
            windSpeed = wind.speed,
            visibility = visibility
        )
    }
}

