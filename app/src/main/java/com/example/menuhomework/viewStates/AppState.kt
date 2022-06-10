package com.example.menuhomework.viewStates

sealed class AppState<E, T : AppStateEntity<E>> {
    data class Success<E, T : AppStateEntity<E>>(val data: T) : AppState<E, T>()
    data class Error<E, T : AppStateEntity<E>>(val error: String) : AppState<E, T>()
}