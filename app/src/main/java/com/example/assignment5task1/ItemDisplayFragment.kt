package com.example.assignment5task1

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment


private const val ARG_ITEM_ID = "id"
class ItemDisplayFragment : Fragment() {

    private var itemID: Int? = null

    private lateinit var itemDisplay:TextView
    private lateinit var itemDetailsDisplay:TextView
    private lateinit var itemQtyDisplay:TextView
    private lateinit var itemSizeDisplay:TextView
    private lateinit var urgentIconDisplay:ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        //get item details for db

        //display item detials in views
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //launch edit item fragment
        return super.onOptionsItemSelected(item)
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