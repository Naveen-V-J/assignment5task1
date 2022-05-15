package com.example.assignment5task1

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    interface OnClick{
        fun displayItemActivity(itemID: Int)
        fun addItemActivity()
    }

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var dbHelper: DBHelper
    private lateinit var recyclerView:RecyclerView
    private lateinit var adapter:ItemAdapter
    private lateinit var itemList: MutableList<Item>
    private lateinit var callback:OnClick
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_home, container, false)
        dbHelper=DBHelper(requireActivity())
        callback=activity as OnClick
        populateItemList()
        recyclerView=view.findViewById(R.id.itemListRecyclerView)
        adapter=ItemAdapter(itemList)
        adapter.onItemClick={
            callback.displayItemActivity(it.id)

        }
        adapter.onItemLongClick={
            deleteItem(it)
        }
        adapter.onAddItemClicked={
            callback.addItemActivity()
        }
        adapter.onItemBought={
            onItemBought(it)
        }
        recyclerView.adapter=adapter
        return view
    }

    private fun onItemBought(item: Item) {
        dbHelper.updateItemBought(item)
        adapter.itemList=dbHelper.getItemsWhereBought(false)
        adapter.notifyDataSetChanged()
    }

    private fun deleteItem(item: Item){
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setMessage("Are you want to delete ${item.name} from the list?").setCancelable(true)
            .setPositiveButton("DELETE", DialogInterface.OnClickListener { dialogInterface, i ->
            dbHelper.deleteItem(item.id)
            adapter.itemList=dbHelper.getItemsWhereBought(false)
            adapter.notifyDataSetChanged()
            dialogInterface.dismiss()})
            .setNegativeButton("CANCEL", DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.cancel() })

    }

    private fun populateItemList() {
        itemList=dbHelper.getItemsWhereBought(false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}