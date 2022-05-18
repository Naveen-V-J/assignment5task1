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
private const val ARG_BOUGHT = "bought"

class ItemListFragment : Fragment() {
    interface OnClick{
        fun displayItemActivity(itemID: Int)
        fun addItemActivity()
        fun goToUrgentList()
        fun goToCompletedList()
        fun goToHome()
    }

    private var onlyUrgent: Int? = null
    private var onlyBought: Boolean?=null

    private lateinit var dbHelper: DBHelper
    private lateinit var recyclerView:RecyclerView
    private lateinit var adapter:ItemAdapter
    private lateinit var fab:FloatingActionButton
    private lateinit var callback:OnClick


    private lateinit var urgentButton:Button
    private lateinit var completeButton: Button
    private lateinit var homeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper=DBHelper(requireActivity())
        callback=activity as OnClick
        arguments?.let {
            onlyUrgent = it.getInt(ARG_URGENT)
            onlyBought=it.getBoolean(ARG_BOUGHT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_home, container, false)
        urgentButton=view.findViewById(R.id.swapButtonUrgent)
        urgentButton.setOnClickListener {
            callback.goToUrgentList()
        }
        completeButton=view.findViewById(R.id.swapButtonComp)
        completeButton.setOnClickListener {
            callback.goToCompletedList()
        }
        homeButton=view.findViewById(R.id.swapButtonHome)
        homeButton.setOnClickListener{
            callback.goToHome()
        }

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
        fab=view.findViewById(R.id.addItemFab)
        fab.setOnClickListener(){
            callback.addItemActivity()
        }

        adapter=ItemAdapter(dbHelper.getBoughtItems(),onlyBought!!)
        adapter.onItemClick={
            callback.displayItemActivity(it.id)

        }
        recyclerView.adapter=adapter
    }

    private fun initUIUnBought(view: View){
        recyclerView=view.findViewById(R.id.itemListRecyclerView)
        recyclerView.layoutManager=LinearLayoutManager(requireContext())
        fab=view.findViewById(R.id.addItemFab)
        fab.setOnClickListener(){
            callback.addItemActivity()
        }

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
        if (onlyBought!!){
            adapter.itemList=dbHelper.getBoughtItems()
        }else{
            adapter.itemList=dbHelper.getUnboughtItems(onlyUrgent!!)
        }
        adapter.notifyDataSetChanged()

    }


    companion object {
        @JvmStatic
        fun newInstance(onlyUrgent: Int, onlyBought:Boolean) =
            ItemListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_URGENT, onlyUrgent)
                    putBoolean(ARG_BOUGHT,onlyBought)
                }
            }
    }
}