package com.example.assignment5task1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*


private const val ARG_ITEM_ID = "id"

class ItemEditFragment : Fragment() {

    interface OnClick{
        fun returnToDisplayFragment(itemID: Int)
    }
    private lateinit var callback:OnClick

    private var itemID: Int? = null
    private lateinit var item:Item

    private lateinit var dbHelper: DBHelper
    private lateinit var editItemDisplay:EditText
    private lateinit var editItemDetailsDisplay:EditText
    private lateinit var arrowDownImageView:ImageView
    private lateinit var arrowUpImageView:ImageView
    private lateinit var editItemQtyDisplay:TextView
    private lateinit var sizeSpinner:Spinner
    private lateinit var editUrgentCheckBox:CheckBox
    private lateinit var editToListButton:Button
    private var qty=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper= DBHelper(requireContext())
        callback=activity as OnClick
        arguments?.let {
            itemID = it.getInt(ARG_ITEM_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_item_edit, container, false)
        Log.d("EDITFRAG","LAUNCHED")

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

        //get item from db
        item= dbHelper.getItem(itemID!!)!!

        //display item details on the views
        editItemDisplay.setText(item.name)
        editItemDetailsDisplay.setText(item.details)
        qty= item.qty
        editItemQtyDisplay.setText(qty.toString())
        editUrgentCheckBox.isChecked = item.urgent ==1



        val arrayAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sizes_array,
            android.R.layout.simple_spinner_item
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sizeSpinner.adapter=arrayAdapter
        sizeSpinner.setSelection(arrayAdapter.getPosition(item.size))

        if (qty==1){
            arrowDownImageView.isEnabled=false
        }
        arrowDownImageView.setOnClickListener{
            onClickDown()
        }
        arrowUpImageView.setOnClickListener {
            onClickUp()
        }

        //set on click listener to edit button
        editToListButton.setOnClickListener{
            if (validateInput()){
                editItem()
                callback.returnToDisplayFragment(itemID!!)
            }
        }

    }

    private fun validateInput(): Boolean {
        if (editItemDisplay.text.isNullOrEmpty()){
            editItemDisplay.setError("Please enter the item to be purchased")
            return false
        }else{
            return true
        }
    }

    private fun editItem(){
        if (item.bought==1){
            return
        }
        val isUrgent = if (editUrgentCheckBox.isChecked){
            1
        }else
            0
        val newItem = Item(itemID!!,editItemDisplay.text.toString(),editItemDetailsDisplay.text.toString(),editItemQtyDisplay.text.toString().toInt(),sizeSpinner.selectedItem.toString(),isUrgent,item.bought)

        dbHelper.updateItem(newItem)

    }

    fun onClickUp(){
        qty+=1
        editItemQtyDisplay.setText(qty.toString())
        arrowDownImageView.isEnabled=true
    }

    fun onClickDown(){
        qty-=1
        editItemQtyDisplay.setText(qty.toString())
        if (qty==1)
            arrowDownImageView.isEnabled=false

    }

    companion object {
        @JvmStatic
        fun newInstance(itemID: Int) =
            ItemEditFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ITEM_ID, itemID)
                }
            }
    }
}