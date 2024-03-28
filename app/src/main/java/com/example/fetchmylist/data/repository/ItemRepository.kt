package com.example.fetchmylist.data.repository

import com.example.fetchmylist.data.model.Item
import com.example.fetchmylist.data.network.ItemAPIService
import retrofit2.Response

class ItemRepository(private val api: ItemAPIService) {

    suspend fun getItems(): Response<List<Item>> {
        return api.getItems()
    }
}
