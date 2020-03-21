package com.example.mobiquity.injection.component

import com.example.mobiquity.injection.module.NetworkModule
import com.example.mobiquity.ui.item.ItemAdapterViewModel
import com.example.mobiquity.ui.item.ItemViewListModel
import com.example.mobiquity.ui.product.ProductAdapterViewModel
import com.example.mobiquity.ui.product.ProductViewListModel
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified ItemViewListModel.
     * @param itemViewListModel ItemViewListModel in which to inject the dependencies
     */
    fun inject(itemViewListModel: ItemViewListModel)
    /**
     * Injects required dependencies into the specified ItemAdapterViewModel.
     * @param itemAdapterViewModel ItemAdapterViewModel in which to inject the dependencies
     */
    fun inject(itemAdapterViewModel: ItemAdapterViewModel)

    /**
     * Injects required dependencies into the specified ProductViewListModel.
     * @param productViewListModel ProductViewListModel in which to inject the dependencies
     */
    fun inject(productViewListModel: ProductViewListModel)

    /**
     * Injects required dependencies into the specified ProductAdapterViewModel.
     * @param productAdapterViewModel ProductAdapterViewModel in which to inject the dependencies
     */
    fun inject(productAdapterViewModel: ProductAdapterViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}