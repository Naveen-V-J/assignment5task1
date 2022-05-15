package com.example.assignment5task1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(),HomeFragment.OnClick {

    lateinit var bottomNav:BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportFragmentManager.beginTransaction().add(R.id.root,HomeFragment()).commit()

    }


    override fun displayItemActivity(itemID: Int) {
        val intent= Intent(this,ItemActivity::class.java)
        intent.putExtra("id",itemID)
        startActivity(intent)
    }

    override fun addItemActivity() {
        val resultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode==Activity.RESULT_OK){
                supportFragmentManager.beginTransaction().replace(R.id.root,HomeFragment()).commit()
            }
        }
        val intent= Intent(this,ItemActivity::class.java)
        resultLauncher.launch(intent)
    }
}