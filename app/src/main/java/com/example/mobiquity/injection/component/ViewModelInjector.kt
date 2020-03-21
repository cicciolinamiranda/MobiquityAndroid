package com.example.mobiquity.injection.component

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.mobiquity.injection.module.NetworkModule
import com.example.mobiquity.repository.database.AppDatabase
import com.example.mobiquity.ui.item.ItemViewListModel
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
     * Injects required dependencies into the specified ArticleViewModel.
     * @param articleViewModel ArticleViewModel in which to inject the dependencies
     */
//    fun inject(articleViewModel: ArticleViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}