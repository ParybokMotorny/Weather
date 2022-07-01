package com.example.menuhomework.model.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity
@Parcelize
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    var city: String,

    var temp: Float,

    var pressure: Int,

    var humidity: Int,

    var date: Date,

    var icon: String,

    var allClouds: Int,

    var windSpeed: Float,

    var windDeg: Float,

    var visibility: Int

) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WeatherEntity

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}