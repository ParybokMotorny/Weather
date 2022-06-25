package com.example.menuhomework.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.coroutines.CoroutineContext

/*
E - type of input parameters
for example Weather, List<Weather> etc.
 */

abstract class BaseViewModel<T>
    : ViewModel(),
    CoroutineScope {
    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Default + Job()
    }

    private val viewStateChannel = BroadcastChannel<T>(Channel.CONFLATED)

    private val errorChannel = Channel<Throwable>()

    open fun getViewState(): ReceiveChannel<T> = viewStateChannel.openSubscription()

    open fun getErrorChannel(): ReceiveChannel<Throwable> = errorChannel

    protected fun setError(e: Throwable) {
        launch {
            errorChannel.send(e)
        }
    }

    protected fun setData(data: T) {
        launch {
            viewStateChannel.send(data)
        }
    }

    override fun onCleared() {
        viewStateChannel.close()
        errorChannel.close()
        coroutineContext.cancel()

        super.onCleared()
    }
}