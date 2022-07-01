package com.example.menuhomework.model.database

import androidx.room.*

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWeather(weather: WeatherEntity) : Long

    @Update
    fun updateWeather(weather: WeatherEntity)

    @Delete
    fun deleteWeather(weather: WeatherEntity)

    @Query("DELETE FROM weatherentity WHERE id = :id")
    fun deleteWeatherById(id: Long)

    @Query("SELECT * FROM weatherentity")
    fun getAllWeathers(): List<WeatherEntity>

    @Query("SELECT * FROM weatherentity WHERE id = :id")
    fun getWeatherById(id: Long): WeatherEntity

    @Query("SELECT COUNT() FROM weatherentity")
    fun getCountWeathers(): Long

    @Query("DELETE FROM weatherentity")
    fun deleteAll()

    @Query("SELECT * FROM weatherentity ORDER BY " +
            "CASE WHEN :isAsc = 1 THEN city END ASC, " +
            "CASE WHEN :isAsc = 2 THEN city END DESC ")
    fun getAllSortedByName(isAsc : Int): List<WeatherEntity>

    @Query("SELECT * FROM weatherentity ORDER BY " +
            "CASE WHEN :isAsc = 1 THEN date END ASC, " +
            "CASE WHEN :isAsc = 2 THEN date END DESC ")
    fun getAllSortedByDate(isAsc : Int): List<WeatherEntity>
}