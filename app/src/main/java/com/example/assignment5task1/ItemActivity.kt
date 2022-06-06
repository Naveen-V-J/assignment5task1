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

        //if itemId is present in intent then it is for displaying the item
        //if no itemId then it is to add new item.
        val itemID=intent.getIntExtra("id",-1)
        if (itemID==-1){
            supportFragmentManager.beginTransaction().add(R.id.item_activity_layout,ItemAddFragment()).commit()
        }else{
            val itemDisplayFragment=ItemDisplayFragment.newInstance(itemID)
            supportFragmentManager.beginTransaction().add(R.id.item_activity_layout,itemDisplayFragment).commit()
        }


    }

    //called from ItemAddFragment when new item has been added.
    override fun returnResultToMainActivity() {
        val intent= Intent()
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

    //To start ItemEditFragment from ItemDisplayFragment
    override fun editItem(itemID: Int) {
        val itemEditFragment=ItemEditFragment.newInstance(itemID)
        Log.d("ITEM ACTIVITY", "IN editItem id: $itemID")
        supportFragmentManager.beginTransaction().replace(R.id.item_activity_layout,itemEditFragment).addToBackStack(null).commit()
    }

    //To go back to ItemDisplayFragment after item was edited
    override fun returnToDisplayFragment(itemID: Int) {
        val itemDisplayFragment=ItemDisplayFragment.newInstance(itemID)
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction().replace(R.id.item_activity_layout,itemDisplayFragment).commit()
    }


}