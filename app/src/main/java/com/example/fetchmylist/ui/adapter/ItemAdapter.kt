package com.example.fetchmylist.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchmylist.R
import com.example.fetchmylist.data.model.Item

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var items: List<Item> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<Item>) {
        items = newList
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewId: TextView = itemView.findViewById(R.id.textViewId)
        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        private val textViewListId: TextView = itemView.findViewById(R.id.textViewListId)

        @SuppressLint("SetTextI18n")
        fun bind(item: Item) {
            textViewId.text = itemView.context.getString(R.string.label_item_id) +"\t"+ item.id.toString()
            textViewName.text =itemView.context.getString(R.string.label_item_name) +"\t"+  item.name
            textViewListId.text = itemView.context.getString(R.string.label_list_id) +"\t"+  item.listId.toString()

        }
    }
}