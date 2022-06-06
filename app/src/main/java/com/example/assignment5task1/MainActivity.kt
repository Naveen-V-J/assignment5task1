package com.example.assignment5task1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(),ItemListFragment.OnClick {
    //use to retrieve fragment from supportFragmentManager
    private val TAG_HOME_FRAGMENT="Shopping List"
    private val TAG_URGENT_FRAGMENT="Urgent Items"
    private val TAG_COMPLETED_FRAGMENT="Bought Items"

    private lateinit var bottomNav:BottomNavigationView
    private lateinit var fab:FloatingActionButton
    private lateinit var resultLauncher:ActivityResultLauncher<Intent>
    private lateinit var menu:Menu


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav=findViewById(R.id.bottomNavigationView)
        menu=bottomNav.menu
        //when a menu in bottom navigation bar is clicked call the appropriate function
        bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home_item->{
                    goToHome()
                    true
                }
                R.id.urgent_list_item->{
                    goToUrgentList()
                    true
                }
                R.id.completed_list_item->{
                    goToCompletedList()
                    true
                }
                else -> {
                    false
                }
            }
        }
        fab=findViewById(R.id.addItemFab)
        fab.setOnClickListener{
            addItemActivity()
        }
        //The home page is displayed at beginning
        //Tag is used to identify each fragment by it's function.
        //Tag is also passed when creating a new ItemListFragment instance to use as it's action bar title
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer,ItemListFragment.newInstance(0,false,TAG_HOME_FRAGMENT),TAG_HOME_FRAGMENT).commit()

        //used when launching ItemActivity to add a new item.
        //When item is added RESULT_OK is returned back to MainActivity and Home page is displayed again.
        resultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode==Activity.RESULT_OK){
                supportFragmentManager.popBackStack()
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,ItemListFragment.newInstance(0,false,TAG_HOME_FRAGMENT),TAG_HOME_FRAGMENT).commit()

            }
        }


    }

    //To start ItemActivity and launch ItemDisplayFragment
    override fun displayItemActivity(itemID: Int) {
        val intent= Intent(this,ItemActivity::class.java)
        //id of the item to be displayed is stored in intent
        intent.putExtra("id",itemID)
        startActivity(intent)
    }

    //To start ItemActivity and launch ItemDisplayFragment
    private fun addItemActivity() {
        val intent= Intent(this,ItemActivity::class.java)
        resultLauncher.launch(intent)
    }

    //Displays the Urgent List page
    private fun goToUrgentList() {
        //checks whether the page currently displayed is Urgent List page
        //if true do nothing. this prevents from relaunching and adding the same page to backstack
        val currentFragment = supportFragmentManager.findFragmentByTag(TAG_URGENT_FRAGMENT)
        if (!(currentFragment != null && currentFragment.isVisible)) {
            //if false replace current page with urgent list page. Add replaced fragment to backstack
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ItemListFragment.newInstance(1, false,TAG_URGENT_FRAGMENT), TAG_URGENT_FRAGMENT)
                .addToBackStack(null).commit()

        }
    }

    //Display the Completed List page
    private fun goToCompletedList() {
        val currentFragment = supportFragmentManager.findFragmentByTag(TAG_COMPLETED_FRAGMENT)
        if (!(currentFragment != null && currentFragment.isVisible)) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ItemListFragment.newInstance(0, true,TAG_COMPLETED_FRAGMENT), TAG_COMPLETED_FRAGMENT)
                .addToBackStack(null).commit()
        }
    }

    //Display the home page
    private fun goToHome() {
        val currentFragment = supportFragmentManager.findFragmentByTag(TAG_HOME_FRAGMENT)
        if (!(currentFragment != null && currentFragment.isVisible)) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ItemListFragment.newInstance(0, false,TAG_HOME_FRAGMENT), TAG_HOME_FRAGMENT)
                .addToBackStack(null).commit()
        }
    }

    //show current page on bottom navigation bar
    override fun setSelectedMenuItem(fragmentTag: String) {
        when(fragmentTag){
            TAG_HOME_FRAGMENT->{
                menu.getItem(1).isChecked = true
            }
            TAG_URGENT_FRAGMENT->{
                menu.getItem(0).isChecked = true
            }
            TAG_COMPLETED_FRAGMENT->{
                menu.getItem(2).isChecked = true
            }

        }
    }

}