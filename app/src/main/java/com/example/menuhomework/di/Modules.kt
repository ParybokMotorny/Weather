package com.example.menuhomework.di

import androidx.room.Room
import com.example.menuhomework.model.Repository
import com.example.menuhomework.model.database.WeatherSource
import com.example.menuhomework.model.database.database.WeatherDatabase
import com.example.menuhomework.model.providers.DataProvider
import com.example.menuhomework.model.providers.InternetProvider
import com.example.menuhomework.model.retrofit.OpenWeatherMapProvider
import com.example.menuhomework.viewmodels.CityViewModel
import com.example.menuhomework.viewmodels.HistoryViewModel
import com.example.menuhomework.viewmodels.MapsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    factory { OpenWeatherMapProvider() } bind InternetProvider::class
    factory { WeatherSource(get()) } bind DataProvider::class
    factory { Repository(get(), get()) }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            WeatherDatabase::class.java,
            "education_database"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
            .weatherDao
    }
}

val mapsModule = module {
    viewModel { MapsViewModel(get()) }
}

val historyModule = module {
    viewModel { HistoryViewModel(get()) }
}

val cityModule = module {
    viewModel { CityViewModel(get()) }
}