package com.example.assignment5task1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ItemEditFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var editItemDisplay:EditText
    private lateinit var editItemDetailsDisplay:EditText
    private lateinit var arrowDownImageView:ImageView
    private lateinit var arrowUpImageView:ImageView
    private lateinit var editItemQtyDisplay:TextView
    private lateinit var sizeSpinner:TextView
    private lateinit var editUrgentCheckBox:CheckBox
    private lateinit var editToListButton:Button


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
        val view=inflater.inflate(R.layout.fragment_item_edit, container, false)
        initUI(view)
        return view
    }

    private fun initUI(view: View) {
        editItemDisplay=view.findViewById(R.id.editItemDisplay)
        editItemDetailsDisplay=view.findViewById(R.id.editItemDetailsDisplay)
        arrowDownImageView=view.findViewById(R.id.arrowDownImageView)
        arrowUpImageView=view.findViewById(R.id.arrowUpImageView)
        editItemQtyDisplay=view.findViewById(R.id.editItemQtyDisplay)
        sizeSpinner=view.findViewById(R.id.sizeSpinner)
        editUrgentCheckBox=view.findViewById(R.id.editUrgentCheckBox)
        editToListButton=view.findViewById(R.id.editToListButton)

        //get item detials from db or as bundle or both?

        //display item details on the views

        //set on click listener to edit button

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemEditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}