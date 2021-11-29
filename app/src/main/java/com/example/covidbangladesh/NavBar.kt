package com.example.covidbangladesh

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle

import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.covidbangladesh.databinding.ActivityNavBarBinding
import com.google.android.gms.common.util.CollectionUtils.setOf


import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_signin.*
import retrofit.Call
import retrofit.Callback
import retrofit.Response
import retrofit.Retrofit
import java.util.*

class NavBar : AppCompatActivity() {

    private lateinit var binding: ActivityNavBarBinding

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_nav_bar)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        val mFab = findViewById<FloatingActionButton>(R.id.floatingButton)

        mFab.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_VIEW)
            callIntent.data = Uri.parse("https://coronatest.brac.net/")
            startActivity(callIntent)
        }

        AppBarConfiguration(
            setOf(
                R.id.Home_Screen, R.id.Map, R.id.About, R.id.Map2
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

}