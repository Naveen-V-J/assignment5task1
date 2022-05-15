package com.example.assignment5task1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class ItemActivity : AppCompatActivity() {
    private lateinit var itemEditText:EditText
    private lateinit var itemDetailsEditText:EditText
    private lateinit var arrowUpImageView:ImageView
    private lateinit var newItemQtyDisplay:TextView
    private lateinit var arrowDownImageView:ImageView
    private lateinit var sizeSpinner:Spinner
    private lateinit var urgentCheckBox:CheckBox
    private lateinit var addToListButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        supportActionBar?.setTitle("Add Item")
        initUI()
    }

    private fun initUI() {
        itemEditText=findViewById(R.id.itemDisplay)
        itemDetailsEditText=findViewById(R.id.itemDetailsDisplay)
        arrowUpImageView=findViewById(R.id.arrowUpImageView)
        arrowDownImageView=findViewById(R.id.arrowDownImageView)
        newItemQtyDisplay=findViewById(R.id.itemQtyDisplay)
        sizeSpinner=findViewById(R.id.sizeSpinner)
        urgentCheckBox=findViewById(R.id.urgentCheckBox)
        addToListButton=findViewById(R.id.addToListButton)

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
}