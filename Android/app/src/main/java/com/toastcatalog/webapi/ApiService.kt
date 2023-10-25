package com.toastcatalog.webapi

import com.toastcatalog.webapi.dto.Item
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("fa5a29bd-623f-45d0-b2c9-04410875ca7b")
    fun getItems(): Call<List<Item>>
}