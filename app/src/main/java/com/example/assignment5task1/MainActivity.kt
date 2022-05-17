package com.example.assignment5task1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(),HomeFragment.OnClick {

    lateinit var bottomNav:BottomNavigationView
    private lateinit var resultLauncher:ActivityResultLauncher<Intent>
    private lateinit var homeFragment:HomeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        homeFragment=HomeFragment()
        supportFragmentManager.beginTransaction().add(R.id.root,homeFragment).commit()
        resultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode==Activity.RESULT_OK){
                supportFragmentManager.beginTransaction().replace(R.id.root,HomeFragment()).commit()
            }
        }


    }


    override fun displayItemActivity(itemID: Int) {
        val intent= Intent(this,ItemActivity::class.java)
        intent.putExtra("id",itemID)
        startActivity(intent)
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun addItemActivity() {

        val intent= Intent(this,ItemActivity::class.java)
        resultLauncher.launch(intent)
    }
}