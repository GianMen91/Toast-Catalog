package com.toastcatalog

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ItemDetailActivity : AppCompatActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        val itemName = intent.getStringExtra("name")
        val itemPrice = intent.getStringExtra("price")
        val itemCurrency = intent.getStringExtra("currency")
        val itemLastSold = intent.getStringExtra("last_sold")

        if (itemName != null && itemPrice != null && itemCurrency != null && itemLastSold != null) {
            displayItemDetails(itemName, itemPrice, itemCurrency, itemLastSold)
        } else {
            // Handle the case when the item details are not provided
            // You can show an error message or handle it accordingly.
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayItemDetails(name: String, price: String, currency: String, lastSold: String) {
        val itemNameTextView = findViewById<TextView>(R.id.tv_item_name_detail)
        val itemPriceTextView = findViewById<TextView>(R.id.tv_item_price_detail)
        val itemCurrencyTextView = findViewById<TextView>(R.id.tv_item_currency_detail)
        val itemLastSoldTextView = findViewById<TextView>(R.id.tv_item_last_sold_detail)

        itemNameTextView.text = name
        itemPriceTextView.text = price
        itemCurrencyTextView.text = currency

        val zonedTime = ZonedDateTime.parse(lastSold)
        val formatted : String = zonedTime.format(DateTimeFormatter.ofPattern("dd/MM/YYYY, HH:mm"))

        itemLastSoldTextView.text = formatted
    }
}
