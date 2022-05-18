package com.example.assignment5task1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(),ItemListFragment.OnClick {
    private val TAG_HOME_FRAGMENT="home"
    private val TAG_URGENT_FRAGMENT="urgent"
    private val TAG_COMPLETED_FRAGMENT="bought"

    lateinit var bottomNav:BottomNavigationView
    private lateinit var resultLauncher:ActivityResultLauncher<Intent>
    private lateinit var itemListFragment:ItemListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.root,ItemListFragment.newInstance(0,false),TAG_HOME_FRAGMENT).commit()
        resultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode==Activity.RESULT_OK){
                supportFragmentManager.popBackStack()
                supportFragmentManager.beginTransaction().replace(R.id.root,ItemListFragment.newInstance(0,false),TAG_HOME_FRAGMENT).commit()
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
        val currentFragment = supportFragmentManager.findFragmentByTag(TAG_URGENT_FRAGMENT)
        if (!(currentFragment != null && currentFragment.isVisible)) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.root, ItemListFragment.newInstance(1, false), TAG_URGENT_FRAGMENT)
                .addToBackStack(null).commit()

        }
    }

    override fun goToCompletedList() {
        val currentFragment = supportFragmentManager.findFragmentByTag(TAG_COMPLETED_FRAGMENT)
        if (!(currentFragment != null && currentFragment.isVisible)) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.root, ItemListFragment.newInstance(0, true), TAG_COMPLETED_FRAGMENT)
                .addToBackStack(null).commit()
        }
    }


    override fun goToHome() {
        val currentFragment = supportFragmentManager.findFragmentByTag(TAG_HOME_FRAGMENT)
        if (!(currentFragment != null && currentFragment.isVisible)) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.root, ItemListFragment.newInstance(0, false), TAG_HOME_FRAGMENT)
                .addToBackStack(null).commit()
        }
    }

}