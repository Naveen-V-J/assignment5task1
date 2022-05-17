package com.example.assignment5task1

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


private const val ARG_URGENT = "urgent"

class UnBoughtItemsFragment : Fragment() {
    interface OnClick{
        fun displayItemActivity(itemID: Int)
        fun addItemActivity()
        fun goToUrgentList()
    }

    private var onlyUrgent: Int? = null

    private lateinit var dbHelper: DBHelper
    private lateinit var recyclerView:RecyclerView
    private lateinit var adapter:ItemAdapter
    private lateinit var fab:FloatingActionButton
    private lateinit var callback:OnClick
    private lateinit var swapButton:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper=DBHelper(requireActivity())
        callback=activity as OnClick
        arguments?.let {
            onlyUrgent = it.getInt(ARG_URGENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_home, container, false)
        swapButton=view.findViewById(R.id.swapButton)
        if (onlyUrgent==1){
            swapButton.isEnabled=false
            swapButton.visibility=View.GONE
        }else{
            swapButton.setOnClickListener {
                callback.goToUrgentList()
            }
        }
        recyclerView=view.findViewById(R.id.itemListRecyclerView)
        recyclerView.layoutManager=LinearLayoutManager(requireContext())
        fab=view.findViewById(R.id.addItemFab)
        fab.setOnClickListener(){
            callback.addItemActivity()
        }

        adapter=ItemAdapter(dbHelper.getUnboughtItems(onlyUrgent!!))
        adapter.onItemClick={
            callback.displayItemActivity(it.id)

        }
        adapter.onItemLongClick={
            deleteItem(it)
        }

        adapter.onItemBought={
            it.bought=1
            onItemBought(it)
        }
        recyclerView.adapter=adapter
        return view
    }

    private fun onItemBought(item: Item) {
        dbHelper.updateItemBought(item)
        adapter.itemList=dbHelper.getUnboughtItems(onlyUrgent!!)
        adapter.notifyDataSetChanged()
    }

    private fun deleteItem(item: Item){
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setMessage("Are you sure you want to delete ${item.name} from the list?").setCancelable(true)
            .setPositiveButton("DELETE", DialogInterface.OnClickListener { dialogInterface, i ->
            dbHelper.deleteItem(item.id)
            adapter.itemList=dbHelper.getUnboughtItems(onlyUrgent!!)
            adapter.notifyDataSetChanged()
            dialogInterface.dismiss()})
            .setNegativeButton("CANCEL", DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.cancel() })
        dialogBuilder.setTitle("Delete item")
        dialogBuilder.setIcon(R.drawable.important).show()
    }

    override fun onResume() {
        super.onResume()
        adapter.itemList=dbHelper.getUnboughtItems(onlyUrgent!!)
        adapter.notifyDataSetChanged()

    }


    companion object {
        @JvmStatic
        fun newInstance(onlyUrgent: Int) =
            UnBoughtItemsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_URGENT, onlyUrgent)
                }
            }
    }
}