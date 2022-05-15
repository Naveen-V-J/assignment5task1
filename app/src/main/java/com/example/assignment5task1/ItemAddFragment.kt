package com.example.assignment5task1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ItemAddFragment : Fragment() {
    interface ActivityCallback{

    }
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var itemEditText: EditText
    private lateinit var itemDetailsEditText: EditText
    private lateinit var arrowUpImageView: ImageView
    private lateinit var newItemQtyDisplay: TextView
    private lateinit var arrowDownImageView: ImageView
    private lateinit var sizeSpinner: Spinner
    private lateinit var urgentCheckBox: CheckBox
    private lateinit var addToListButton: Button

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
        val view =inflater.inflate(R.layout.fragment_item_add, container, false)
        initUI(view)
        return view
    }

    private fun initUI(view: View) {
        itemEditText=view.findViewById(R.id.itemDisplay)
        itemDetailsEditText=view.findViewById(R.id.itemDetailsDisplay)
        arrowUpImageView=view.findViewById(R.id.arrowUpImageView)
        arrowDownImageView=view.findViewById(R.id.arrowDownImageView)
        newItemQtyDisplay=view.findViewById(R.id.itemQtyDisplay)
        sizeSpinner=view.findViewById(R.id.sizeSpinner)
        urgentCheckBox=view.findViewById(R.id.urgentCheckBox)
        addToListButton=view.findViewById(R.id.addToListButton)

        addToListButton.setOnClickListener(){
            if (validateInput()){
                addItemToList()
            }
        }
    }

    private fun validateInput(): Boolean {
        if (itemEditText.text.isNullOrEmpty()){
            itemEditText.setError("Please enter the item to be purchased")
            return false
        }else{
            return true
        }
    }

    private fun addItemToList(){

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemAddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}