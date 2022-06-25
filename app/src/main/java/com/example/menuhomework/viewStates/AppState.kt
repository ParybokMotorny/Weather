package com.example.menuhomework.viewStates

sealed class AppState<T> {
    data class Success<T>(val data: AppStateEntity<T>) : AppState<T>()
    data class Error<T>(val error: String) : AppState<T>()
}