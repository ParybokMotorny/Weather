package com.example.menuhomework.model.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.menuhomework.model.providers.InternetProvider
import com.example.menuhomework.model.RequestResult

class OpenWeatherMapProvider : InternetProvider {
    override fun request(city: String): LiveData<RequestResult> {
        val result = MutableLiveData<RequestResult>()

        Retrofit({ request ->
            result.value = RequestResult.Success(request)
        }, { exception ->
            result.value = RequestResult.Error(exception)
        })
            .run(
                city,
                "6b0423304b20ad534ccceecc6d3c729a"
            )

        return result
    }

    override fun request(latitude: Float, longitude: Float): LiveData<RequestResult> {
        val result = MutableLiveData<RequestResult>()

        Retrofit({ request ->
            result.value = RequestResult.Success(request)
        }, { exception ->
            result.value = RequestResult.Error(exception)
        })
            .run(
                latitude,
                longitude,
                "6b0423304b20ad534ccceecc6d3c729a"
            )

        return result
    }
}