package com.toastcatalog.webapi

import android.util.Log
import com.toastcatalog.webapi.dto.Item
import com.toastcatalog.webapi.dto.ItemsResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class NetworkClient(private val onDataLoaded: (List<Item>?) -> Unit) {

    fun getItems() {
        val apiService = RetrofitClient.instance

        val call = apiService.getItems()
        call.enqueue(object : Callback<ItemsResponse> {
            override fun onResponse(call: Call<ItemsResponse>, response: Response<ItemsResponse>) {
                if (response.isSuccessful) {
                    val items = response.body()?.items
                    onDataLoaded(items)
                } else {
                    onDataLoaded(null)
                    Log.e(TAG, "API error")
                }
            }

            override fun onFailure(call: Call<ItemsResponse>, t: Throwable) {
                onDataLoaded(null)
                Log.e(TAG, "Network request failed", t)
            }
        })
    }

    companion object {
        private const val TAG = "NetworkClient"
    }
}
