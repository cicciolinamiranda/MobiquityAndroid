package com.example.mobiquity.network

import com.example.mobiquity.repository.model.Item
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * The interface which provides methods to get result of webservices
 */
interface ItemApi {
    /**
     * Get items from the API
     */
    @GET
    fun getItems(): Observable<List<Item>>
}