package com.example.covidbangladesh

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            Handler().postDelayed(Runnable
            { // This method will be executed once the timer is over
                val i = Intent(this, NavBar::class.java)
                startActivity(i)
                finish()
            }, 3000
            )

    }
}