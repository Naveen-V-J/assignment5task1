package com.example.assignment5task1

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ItemAdapter(var itemList:MutableList<Item>, val isBoughtLayout:Boolean): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    //lambdas implemented in ItemListFragment
    //pass an Item object and returns nothing
    var onItemClick: (Item) -> Unit = {}
    var onItemLongClick: (Item) -> Unit = {}
    var onItemBought: (Item) -> Unit = {}

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val itemNameTextView=itemView.findViewById<TextView>(R.id.itemNameTextView)
        val itemQtyTextView=itemView.findViewById<TextView>(R.id.itemQtyTextView)
        val itemSizeTextView=itemView.findViewById<TextView>(R.id.itemSizeTextView)
        val urgentImageView=itemView.findViewById<ImageView>(R.id.urgentImageView)

        //not present in bought item list
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        val itemBoughtSwitch=itemView.findViewById<Switch>(R.id.itemBoughtSwitch)

        //present in bought item list
        val dateHeading=itemView.findViewById<TextView>(R.id.date_heading)
        val dateTextView=itemView.findViewById<TextView>(R.id.dateTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_list_row,parent,false)
        val viewHolder = ViewHolder(view)
        //set on click listeners
        view.setOnClickListener{
            onItemClick(itemList.get(viewHolder.adapterPosition))
        }
        //long click listener not present in bought items page
        if (!isBoughtLayout){
            view.setOnLongClickListener {
                onItemLongClick(itemList.get(viewHolder.adapterPosition))
                true
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        val item = itemList.get(position)


        holder.itemNameTextView.setText(item.name)
        holder.itemQtyTextView.setText("Qty: ${item.qty}")
        holder.itemSizeTextView.setText("Size: ${item.size}")
        holder.itemBoughtSwitch.setOnCheckedChangeListener(null)
        holder.itemBoughtSwitch.isChecked=false

        if (isBoughtLayout){

            holder.urgentImageView.setImageResource(R.drawable.bought)
            //when displaying bought items do not display the switch but display the date
            holder.itemBoughtSwitch.visibility=View.GONE
            holder.dateTextView.setText(item.dateBought)

        }else{
            //when displaying either home page or urgent list page hide date.
            holder.dateTextView.visibility=View.GONE
            holder.dateHeading.visibility=View.GONE
            //set checkedChangeListener to null before unchecking checkboxes
            holder.itemBoughtSwitch.setOnCheckedChangeListener(null)
            holder.itemBoughtSwitch.isChecked=false

            if (item.urgent.equals(1)){
                holder.urgentImageView.setImageResource(R.drawable.urgent)
            }else{
                holder.urgentImageView.setImageResource(R.drawable.buy)
            }
            //set checkedChangeListener to switch
            holder.itemBoughtSwitch.setOnCheckedChangeListener { _, _ ->
                onItemBought(item)
            }
        }

    }


    override fun getItemCount(): Int {
        return itemList.size
    }

}