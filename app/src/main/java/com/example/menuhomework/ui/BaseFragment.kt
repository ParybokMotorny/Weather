package com.example.menuhomework.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.menuhomework.viewStates.AppState
import com.example.menuhomework.viewStates.AppStateEntity
import com.example.menuhomework.viewmodels.BaseViewModel

abstract class BaseFragment<E, VB : ViewBinding, T : AppStateEntity<E>, VM : BaseViewModel<E, T>>
//abstract class BaseFragment<E, VB : ViewBinding, T, VM : BaseViewModel<E, T>>
    (
    @LayoutRes private val layoutId: Int
) : Fragment(layoutId) {

    val binding by lazy { bindView() }

    abstract fun bindView(): VB

    abstract val viewModel: VM

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.liveData.observe(viewLifecycleOwner) {
            renderData(it)
        }
    }

    protected open fun renderData(data: AppState<E, T>) {
        when (data) {
            is AppState.Success -> renderSuccess(data.data)
            is AppState.Error -> renderError(data.error)
        }
    }

    abstract fun renderSuccess(data: T)

    abstract fun renderError(error: String)
}