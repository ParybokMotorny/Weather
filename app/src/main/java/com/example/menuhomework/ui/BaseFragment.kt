package com.example.menuhomework.ui

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.menuhomework.R
import com.example.menuhomework.viewmodels.BaseViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment<E, VB : ViewBinding>
//abstract class BaseFragment<E, VB : ViewBinding, T, VM : BaseViewModel<E, T>>
    (
    @LayoutRes private val layoutId: Int
) : Fragment(layoutId), CoroutineScope {

    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Main + Job()
    }

    private lateinit var dataJob: Job
    private lateinit var errorJob: Job

    val binding by lazy { bindView() }

    abstract fun bindView(): VB

    abstract val viewModel: BaseViewModel<E>

    override fun onStart() {
        super.onStart()
        dataJob = launch {
            viewModel.getViewState().consumeEach {
                renderSuccess(it)
            }
        }

        errorJob = launch {
            viewModel.getErrorChannel().consumeEach {
                renderError(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
    }

    abstract fun renderSuccess(data: E)

    protected open fun renderError(error: Throwable) {
        error.message?.let { showError(it) }
    }

    protected fun showError(error: String) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(R.string.ok_bth_title) { dismiss() }
            show()
        }
    }
}