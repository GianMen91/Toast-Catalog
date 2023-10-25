package com.toastcatalog

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.toastcatalog.R
import com.toastcatalog.webapi.dto.Item
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    private val items = mutableListOf<Item>()
    private var onItemClickListener: ((Item) -> Unit)? = null

    fun setOnItemClickListener(listener: (Item) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemID = itemView.findViewById<TextView>(R.id.tv_item_id)
        private val itemName = itemView.findViewById<TextView>(R.id.tv_item_name)
        private val itemPrice = itemView.findViewById<TextView>(R.id.tv_item_price)
        private val itemLastSold = itemView.findViewById<TextView>(R.id.tv_item_last_sold)

        init {
            itemView.setOnClickListener {
                onItemClickListener?.invoke(items[adapterPosition])
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: Item) {
            itemID.text = item.id.toString()
            itemName.text = item.name
            itemPrice.text = item.price.toString()
            val currency = item.currency
            if (currency == "EUR") {
                itemPrice.text = "${item.price} €"
            } else {
                itemPrice.text = "${item.price} $currency"
            }

            val zonedTime = ZonedDateTime.parse(item.last_sold)
            val formatted : String = zonedTime.format(DateTimeFormatter.ofPattern("dd/MM/YYYY, HH:mm"))

            itemLastSold.text = formatted
        }
    }

    // Update the adapter with a new list of items
    fun setItems(newItems: List<Item>) {
        items.clear()
        items.addAll(newItems)
    }

    fun getItems(): List<Item> {
        return items
    }
}
