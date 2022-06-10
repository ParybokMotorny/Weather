package com.example.menuhomework.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.menuhomework.viewStates.AppState
import com.example.menuhomework.viewStates.AppStateEntity
import com.example.menuhomework.viewStates.BaseViewState
import java.lang.Error

/*
E - type of input parameters
for example Weather, List<Weather> etc.
 */

abstract class BaseViewModel<E, T : AppStateEntity<E>>
    : ViewModel() {
    protected val _liveData = MutableLiveData<AppState<E, T>>()
    val liveData = _liveData as LiveData<AppState<E, T>>

    protected fun emitSuccess(data: T) {
        val state = AppState.Success(data)
        _liveData.postValue(state)
    }

    protected fun emitError(error: String) {
        val state = AppState.Error<E, T>(error)
        _liveData.postValue(state)
    }
}