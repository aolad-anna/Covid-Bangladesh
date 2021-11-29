package com.example.covidbangladesh

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.EditText
import android.widget.Toast
import com.example.covidbangladesh.api.ApiClient
import com.example.covidbangladesh.api.ApiInterface
import com.example.covidbangladesh.models.Login


import kotlinx.android.synthetic.main.activity_signin.*
import retrofit.Call
import retrofit.Callback
import retrofit.Response
import retrofit.Retrofit
import com.example.covidbangladesh.storage.SharedPrefManager
import android.content.SharedPreferences


class signin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        var sharedpref: SharedPreferences?
        val editTextTextEmailAddress: EditText =
            findViewById<View>(R.id.editTextTextEmailAddress) as EditText
        val editTextTextPassword: EditText =
            findViewById<View>(R.id.editTextTextPassword) as EditText
        val btnLogin: Button = findViewById<View>(R.id.btnLogin) as Button
        fun isOnline(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
            return false
        }
        progress123.visibility=View.GONE

        fun CharSequence?.isValidEmail() = !isNullOrEmpty() && !Patterns.EMAIL_ADDRESS.matcher(this).matches()
        btnLogin.setOnClickListener {
            val email = editTextTextEmailAddress.text.toString().trim()
            val password = editTextTextPassword.text.toString().trim()

             if (email.isEmpty()) {
                Toast.makeText(
                    applicationContext, "Data is missing", Toast.LENGTH_LONG
                ).show()
                editTextTextEmailAddress.error = "Email required"
                editTextTextEmailAddress.requestFocus()
                return@setOnClickListener
            }
             else if (email.isValidEmail()) {
                 editTextTextEmailAddress.error = "Invalid email address"
                 editTextTextEmailAddress.requestFocus()
                 return@setOnClickListener
             }
            else if (password.isEmpty()) {
                editTextTextPassword.error = "Password required"
                editTextTextPassword.requestFocus()
                return@setOnClickListener
            }

            else if (email.isNotEmpty() && password.isNotEmpty())
            {
                progress123.visibility=View.VISIBLE
                btnLogin.visibility=View.GONE
            }
            val apiService = ApiClient.client!!.create(ApiInterface::class.java)
            val call: Call<Login> = apiService.getLogin(email,password)
            call.enqueue(object : Callback<Login> {
                @SuppressLint("SetTextI18n", "CommitPrefEdits")
                override fun onResponse(response: Response<Login>?, retrofit: Retrofit?) {
                        if (response != null) {
                            if (response.body().status == 1) {
                                progress123.visibility = View.GONE
                                btnLogin.visibility = View.VISIBLE
                                SharedPrefManager.getInstance(applicationContext).saveUser(response.body()?.data!!)
                                Toast.makeText(
                                    applicationContext,
                                    "Successfully Login",
                                    Toast.LENGTH_LONG
                                ).show()
                                val intent = Intent(applicationContext, NavBar::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                                startActivity(intent)
                            } else {
                                progress123.visibility = View.GONE
                                btnLogin.visibility = View.VISIBLE
                                Toast.makeText(
                                    applicationContext,
                                    "Invalid User Id or Password!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }

                override fun onFailure(t: Throwable?) {
                    Log.d("onFailure", t.toString());
                }

            })


        }

            val sign_up = findViewById<View>(R.id.context) as Button
            sign_up.setOnClickListener { view ->
                val intent = Intent(view.context, signup::class.java)
                view.context.startActivity(intent)
            }
            val conti = findViewById<View>(R.id.conti) as Button
            conti.setOnClickListener { view ->
                val intent = Intent(view.context, NavBar::class.java)
                view.context.startActivity(intent)
            }
            val LButton = findViewById<View>(R.id.logo) as ImageView
            LButton.setOnClickListener { view ->
                val intent = Intent(view.context, NavBar::class.java)
                view.context.startActivity(intent)
            }
            val NButton = findViewById<View>(R.id.notify5) as ImageView
            NButton.setOnClickListener { view ->
                val intent = Intent(view.context, notify::class.java)
                view.context.startActivity(intent)
            }
        }
    override fun onStart() {
        super.onStart()

        if(SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, profile::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        val intent = Intent(applicationContext, NavBar::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)
    }
}
