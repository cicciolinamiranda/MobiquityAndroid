package com.example.mobiquity.base

import androidx.lifecycle.ViewModel
import com.example.mobiquity.injection.component.DaggerViewModelInjector
import com.example.mobiquity.injection.component.ViewModelInjector
import com.example.mobiquity.injection.module.NetworkModule
import com.example.mobiquity.ui.item.ItemViewListModel

abstract class BaseViewModel: ViewModel(){
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is ItemViewListModel -> injector.inject(this)
//            is ItemViewModel -> injector.inject(this)
        }
    }
}