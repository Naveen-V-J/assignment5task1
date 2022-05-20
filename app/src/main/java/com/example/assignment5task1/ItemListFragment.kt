package com.example.assignment5task1

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


private const val ARG_URGENT = "Urgent Items"
private const val ARG_BOUGHT = "Items Bought"
private const val ARG_FRAGMENT_TAG="tag"

class ItemListFragment : Fragment() {
    interface OnClick{
        fun displayItemActivity(itemID: Int)
        fun setSelectedMenuItem(fragmentTag:String)
    }

    private var onlyUrgent: Int? = null
    private var onlyBought: Boolean?=null
    private var fragmentTag: String?=null

    private lateinit var dbHelper: DBHelper
    private lateinit var recyclerView:RecyclerView
    private lateinit var adapter:ItemAdapter
    private lateinit var callback:OnClick


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper=DBHelper(requireActivity())
        callback=activity as OnClick
        arguments?.let {
            onlyUrgent = it.getInt(ARG_URGENT)
            onlyBought=it.getBoolean(ARG_BOUGHT)
            fragmentTag=it.getString(ARG_FRAGMENT_TAG)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_item_list, container, false)
        if (onlyBought!!){
            initUIBought(view)
        }else{
            initUIUnBought(view)
        }
        return view
    }

    private fun initUIBought(view: View) {

        recyclerView=view.findViewById(R.id.itemListRecyclerView)
        recyclerView.layoutManager=LinearLayoutManager(requireContext())

        adapter=ItemAdapter(dbHelper.getBoughtItems(),onlyBought!!)
        adapter.onItemClick={
            callback.displayItemActivity(it.id)

        }
        recyclerView.adapter=adapter
    }

    private fun initUIUnBought(view: View){
        recyclerView=view.findViewById(R.id.itemListRecyclerView)
        recyclerView.layoutManager=LinearLayoutManager(requireContext())

        adapter=ItemAdapter(dbHelper.getUnboughtItems(onlyUrgent!!),onlyBought!!)
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
        (activity as AppCompatActivity).supportActionBar?.setTitle(fragmentTag)
        callback.setSelectedMenuItem(fragmentTag!!)

        if (onlyBought!!){
            adapter.itemList=dbHelper.getBoughtItems()
        }else{
            adapter.itemList=dbHelper.getUnboughtItems(onlyUrgent!!)
        }
        adapter.notifyDataSetChanged()



    }


    companion object {
        @JvmStatic
        fun newInstance(onlyUrgent: Int, onlyBought:Boolean, fragmentTag: String) =
            ItemListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_URGENT, onlyUrgent)
                    putBoolean(ARG_BOUGHT,onlyBought)
                    putString(ARG_FRAGMENT_TAG,fragmentTag)
                }
            }
    }
}