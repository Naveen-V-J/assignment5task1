package com.example.assignment5task1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(),UnBoughtItemsFragment.OnClick {

    lateinit var bottomNav:BottomNavigationView
    private lateinit var resultLauncher:ActivityResultLauncher<Intent>
    private lateinit var unBoughtItemsFragment:UnBoughtItemsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.root,UnBoughtItemsFragment.newInstance(0)).commit()
        resultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode==Activity.RESULT_OK){
                supportFragmentManager.popBackStack()
                supportFragmentManager.beginTransaction().replace(R.id.root,UnBoughtItemsFragment.newInstance(0)).commit()
            }
        }


    }


    override fun displayItemActivity(itemID: Int) {
        val intent= Intent(this,ItemActivity::class.java)
        intent.putExtra("id",itemID)
        startActivity(intent)
    }



    override fun addItemActivity() {

        val intent= Intent(this,ItemActivity::class.java)
        resultLauncher.launch(intent)
    }

    override fun goToUrgentList() {
        supportFragmentManager.beginTransaction().replace(R.id.root,UnBoughtItemsFragment.newInstance(1)).addToBackStack(null).commit()
    }
}