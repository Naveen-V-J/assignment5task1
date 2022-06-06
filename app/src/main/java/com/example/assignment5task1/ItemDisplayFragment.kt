package com.example.assignment5task1

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


private const val ARG_ITEM_ID = "id"
class ItemDisplayFragment : Fragment() {
    interface OnClick{
        fun editItem(itemID: Int)
    }
    private var itemID: Int? = null

    private lateinit var callback:OnClick
    private lateinit var dbHelper: DBHelper
    private lateinit var itemDisplay:TextView
    private lateinit var itemDetailsDisplay:TextView
    private lateinit var itemQtyDisplay:TextView
    private lateinit var itemSizeDisplay:TextView
    private lateinit var urgentIconDisplay:ImageView
    private lateinit var item: Item


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper= DBHelper(requireContext())
        callback=activity as OnClick
        setHasOptionsMenu(true)
        arguments?.let {
            itemID = it.getInt(ARG_ITEM_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_item_display, container, false)
        initUI(view)
        return view
    }

    private fun initUI(view: View) {
        itemDisplay=view.findViewById(R.id.itemDisplay)
        itemDetailsDisplay=view.findViewById(R.id.itemDetailsDisplay)
        itemQtyDisplay=view.findViewById(R.id.itemQtyDisplay)
        itemSizeDisplay=view.findViewById(R.id.itemSizeDisplay)
        urgentIconDisplay=view.findViewById(R.id.urgentIconDisplay)

        item=dbHelper.getItem(itemID!!)!!

        itemDisplay.setText(item.name)
        itemDetailsDisplay.setText(item.details)
        itemQtyDisplay.setText(item.qty.toString())
        itemSizeDisplay.setText(item.size)
        if (item.urgent.equals(0)){
            urgentIconDisplay.setImageResource(R.drawable.unchecked)
        }else{
            urgentIconDisplay.setImageResource(R.drawable.checked)
        }
    }

    //inflate options menu with edit item button
    //only when item is not bought
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (item.bought==0){
            inflater.inflate(R.menu.appbar_menu, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    //when click edit item button callback to itemActivity to launch ItemEditFragment fragment
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        callback.editItem(itemID!!)
        return super.onOptionsItemSelected(item)
    }

    //display action bar title
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setTitle("Display Item")
    }

    companion object {

        @JvmStatic
        fun newInstance(itemID: Int) =
            ItemDisplayFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ITEM_ID, itemID)
                }
            }
    }
}