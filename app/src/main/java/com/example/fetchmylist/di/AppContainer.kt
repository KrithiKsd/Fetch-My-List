package com.example.fetchmylist.di

import com.example.fetchmylist.data.network.ItemAPIService
import com.example.fetchmylist.data.network.RetrofitInstance
import com.example.fetchmylist.data.repository.ItemRepository

class AppContainer {
    private val api= RetrofitInstance.getRetrofitInstance().create(ItemAPIService::class.java)
    val itemRepo= ItemRepository(api)
}
