package com.example.assignment5task1

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class ItemDisplayFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var itemDisplay:TextView
    private lateinit var itemDetailsDisplay:TextView
    private lateinit var itemQtyDisplay:TextView
    private lateinit var itemSizeDisplay:TextView
    private lateinit var urgentIconDisplay:ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        fun newInstance(param1: String, param2: String) =
            ItemDisplayFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}