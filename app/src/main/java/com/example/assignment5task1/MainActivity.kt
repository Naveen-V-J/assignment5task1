package com.example.assignment5task1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        val intent= Intent(this,ItemActivity::class.java)
        startActivity(intent)
    }
}