package com.toastcatalog.webapi

import com.toastcatalog.webapi.dto.ItemsResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("GianMen91/0f93444fade28f5755479464945a7ad1/raw/f7ad7a60b2cff021ecf6cf097add060b39a1742b/toast_list.json")
    fun getItems(): Call<ItemsResponse>
}
