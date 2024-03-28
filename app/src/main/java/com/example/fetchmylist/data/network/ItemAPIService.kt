package com.example.fetchmylist.data.network

import com.example.fetchmylist.data.model.Item
import retrofit2.Response
import retrofit2.http.GET

interface ItemAPIService {

    @GET(value = "/hiring.json")
    suspend fun getItems(): Response<List<Item>>
}
