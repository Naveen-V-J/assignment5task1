package com.example.assignment5task1

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ItemAdapter(var itemList:MutableList<Item>): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    var onItemClick: (Item) -> Unit = {}
    var onItemLongClick: (Item) -> Unit = {}
    var onAddItemClicked = {}
    var onItemBought: (Item) -> Unit = {}

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val itemNameTextView=itemView.findViewById<TextView>(R.id.itemNameTextView)
        val itemQtyTextView=itemView.findViewById<TextView>(R.id.itemQtyTextView)
        val itemSizeTextView=itemView.findViewById<TextView>(R.id.itemSizeTextView)
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        val itemBoughtSwitch=itemView.findViewById<Switch>(R.id.itemBoughtSwitch)
        val addItemFab=itemView.findViewById<FloatingActionButton>(R.id.addItemFab)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_list_row,parent,false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener{
            onItemClick(itemList.get(viewHolder.adapterPosition))
        }
        view.setOnLongClickListener {
            onItemLongClick(itemList.get(viewHolder.adapterPosition))
            true
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        val item = itemList.get(position)
        holder.itemNameTextView.setText(item.name)
        holder.itemQtyTextView.setText("Qty: ${item.qty}")
        holder.itemSizeTextView.setText("Size: ${item.size}")
        holder.itemBoughtSwitch.setOnCheckedChangeListener { _, _ ->

        }
        holder.addItemFab.setOnClickListener(){
            onAddItemClicked()
        }
    }


    override fun getItemCount(): Int {
        return itemList.size
    }

}