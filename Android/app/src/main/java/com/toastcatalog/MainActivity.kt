package com.toastcatalog

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.toastcatalog.webapi.NetworkClient
import com.toastcatalog.webapi.dto.Item
import com.toastcatalog.webapi.dto.saveItemsToFile


class MainActivity : AppCompatActivity() {
    private lateinit var itemsAdapter: ItemsAdapter
    private var isOnline = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        checkNetworkConnection()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_refresh -> {
                checkNetworkConnection()
                // Handle the refresh action when the refresh button is clicked
                if (isOnline) {
                    loadItems() // Load items if online
                } else {
                     showOfflineMessage()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        itemsAdapter = ItemsAdapter()
        recyclerView.adapter = itemsAdapter
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            layoutManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)

        itemsAdapter.setOnItemClickListener { item ->
            // Handle item click: Open the ItemDetailActivity to display item details
            val intent = Intent(this, ItemDetailActivity::class.java)
            intent.putExtra("name", item.name)
            intent.putExtra("price", item.price)
            intent.putExtra("currency", item.currency)
            intent.putExtra("last_sold", item.last_sold)
            startActivity(intent)
        }

    }


    // Check network connection and load items
    private fun checkNetworkConnection() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        isOnline = networkInfo != null && networkInfo.isConnected

        if (isOnline) {
            loadItems()
            // Save the retrieved items to the internal file

        } else {
            // Handle offline mode by showing a message and loading items from the file

            val offlineItems = loadItemsFromFile(this)
            if (offlineItems.isNotEmpty()) {
                itemsAdapter.setItems(offlineItems)
                itemsAdapter.notifyDataSetChanged()
            }else{
                showOfflineMessage()
            }
        }
    }

    // Loading items from the JSON file
    fun loadItemsFromFile(context: Context): List<Item> {
        val items = mutableListOf<Item>()
        try {
            val inputStream = context.openFileInput("items.json")
            val jsonItems = inputStream.bufferedReader().use { it.readText() }
            val gson = Gson()
            val itemType = object : TypeToken<List<Item>>() {}.type
            items.addAll(gson.fromJson(jsonItems, itemType))
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return items
    }



    private fun loadItems() {
        val networkClient = NetworkClient { items ->
            if (items != null) {
                itemsAdapter.setItems(items)
                saveItemsToFile(this,items)
                itemsAdapter.notifyDataSetChanged()
            } else {
                // Handle the case when items cannot be loaded
                showError("Oops! Something went wrong.")
            }
        }

        networkClient.getItems()
    }


    private fun showOfflineMessage() {
        // Display a message indicating offline mode
        showError("No network connection.")
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}