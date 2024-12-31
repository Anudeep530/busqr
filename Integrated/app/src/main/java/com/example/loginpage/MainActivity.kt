package com.example.loginpage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.loginpage.R.id.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContentView(R.layout.act)


        if(SavedSharedPref.getUserName(this).length == 0)
        {
            val i = Intent(this,act::class.java)
            startActivity(i)
        }
        else {
            // Stay at the current activity.

            val navView: NavigationView = findViewById(nav_view)

            val header: View = navView.getHeaderView(0)
            val headerName: TextView = header.findViewById(navHeader_name)

            val sharedPref = getSharedPreferences("mypref", 0)
            val DynUser = sharedPref.getString("svdUser", "")
            headerName.text = DynUser.toString()

            val callback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Handle back button press here
                }
            }
            onBackPressedDispatcher.addCallback(this, callback)


            drawerLayout = findViewById(R.id.drawerLayout)

            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)


            toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()


            val b1 = findViewById<ImageButton>(imageButton)
            b1.setOnClickListener {
                val intent = Intent(this, busQR::class.java)
                startActivity(intent)
            }

            supportActionBar?.setDisplayHomeAsUpEnabled(true)



            navView.setNavigationItemSelectedListener {

                bottomNavigationView.menu.setGroupCheckable(0, false, true)


                it.isChecked = true

                when (it.itemId) {

                    nav_hallticket -> replaceFragement(HallTicket(), it.title.toString())
                    nav_results -> replaceFragement(Results(), it.title.toString())
                    nav_backlogs -> replaceFragement(backlogs(), it.title.toString())
                    nav_requests -> replaceFragement(Requests(), it.title.toString())
                    nav_openelectives -> replaceFragement(OpenElectives(), it.title.toString())
                    nav_coursereg -> replaceFragement(CourseReg(), it.title.toString())
                    nav_settings -> replaceFragement(Settings(), it.title.toString())
                    nav_Help -> replaceFragement(Settings(), it.title.toString())
                    nav_logout -> replaceFragement(Logout(), it.title.toString())

                }
                true
            }

            bottomNavigationView.setOnItemSelectedListener {


                bottomNavigationView.menu.setGroupCheckable(0, true, true)

                it.isChecked = true

                when (it.itemId) {

                    Home -> {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("svdUser", DynUser)
                        startActivity(intent)


                    }
                    R.id.attendance -> replaceFragement(attendance(), it.title.toString())
                    notifications -> replaceFragement(Bnotifiactions(), it.title.toString())
                    receipts -> replaceFragement(Breceipts(), it.title.toString())
                    else -> {

                    }
                }
                true
            }

        }



    }



    private fun replaceFragement(fragment: Fragment, title: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(frame_layout,fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)

    }

    @Override
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){

            return true
        }
        return super.onOptionsItemSelected(item)
    }


}


