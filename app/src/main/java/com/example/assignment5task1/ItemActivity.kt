package com.example.assignment5task1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ItemActivity : AppCompatActivity(),ItemAddFragment.OnClick,ItemDisplayFragment.OnClick,ItemEditFragment.OnClick {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)


        val itemID=intent.getIntExtra("id",-1)
        if (itemID==-1){
            supportActionBar?.setTitle("Add Item")
            supportFragmentManager.beginTransaction().add(R.id.item_activity_layout,ItemAddFragment()).commit()
        }else{
            supportActionBar?.setTitle("Display Item")
            val itemDisplayFragment=ItemDisplayFragment.newInstance(itemID)
            supportFragmentManager.beginTransaction().add(R.id.item_activity_layout,itemDisplayFragment).commit()
        }


    }

    override fun returnResultToMainActivity() {
        val intent= Intent()
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

    override fun editItem(itemID: Int) {
        val itemEditFragment=ItemEditFragment.newInstance(itemID)
        Log.d("ITEM ACTIVITY", "IN editItem id: $itemID")
        supportActionBar?.setTitle("Edit Item")
        supportFragmentManager.beginTransaction().replace(R.id.item_activity_layout,itemEditFragment).addToBackStack(null).commit()
    }

    override fun returnToDisplayFragment(itemID: Int) {
        supportActionBar?.setTitle("Display item")
        val itemDisplayFragment=ItemDisplayFragment.newInstance(itemID)
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction().replace(R.id.item_activity_layout,itemDisplayFragment).commit()
    }


}