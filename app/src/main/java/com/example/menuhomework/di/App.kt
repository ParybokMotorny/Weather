package com.example.menuhomework.di

import android.app.Application
import androidx.room.Room
import com.example.menuhomework.model.database.WeatherDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        db = Room.databaseBuilder(
            applicationContext,
            WeatherDatabase::class.java,
            "education_database"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

        startKoin {
            androidContext(this@App)
            modules(appModule, cityModule, historyModule, mapsModule, databaseModule)
        }
    }


    lateinit var db: WeatherDatabase
}