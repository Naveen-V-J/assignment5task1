package com.example.assignment5task1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ItemActivity : AppCompatActivity(),ItemAddFragment.OnClick,ItemDisplayFragment.OnClick {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        supportActionBar?.setTitle("Add Item")

        val itemID=intent.getIntExtra("id",-1)
        if (itemID==-1){
            supportFragmentManager.beginTransaction().add(R.id.item_activity_layout,ItemAddFragment()).commit()
        }else{
            val itemDisplayFragment=ItemDisplayFragment.newInstance(itemID)
            supportFragmentManager.beginTransaction().add(R.id.item_activity_layout,itemDisplayFragment).commit()
        }


    }

    override fun returnToMainActivity() {
        val intent= Intent()
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

    override fun editItem(itemID: Int) {
        val itemEditFragment=ItemEditFragment.newInstance(itemID)
        supportFragmentManager.beginTransaction().replace(R.id.item_activity_layout,itemEditFragment).addToBackStack(null).commit()
    }


}