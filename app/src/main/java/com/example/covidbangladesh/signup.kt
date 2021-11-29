package com.example.covidbangladesh

import android.Manifest
import android.R.attr
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signup.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.core.app.ActivityCompat.startActivityForResult
import android.R.attr.data

import android.app.Activity
import android.net.Uri
import android.util.Log
import com.google.android.material.internal.ContextUtils.getActivity
import java.io.IOException
import android.R.attr.data
import android.R.attr.data
import android.util.Patterns
import com.example.covidbangladesh.api.ApiClient
import com.example.covidbangladesh.api.ApiInterface
import com.example.covidbangladesh.models.Login
import com.example.covidbangladesh.models.Signup
import kotlinx.android.synthetic.main.activity_signin.*
import retrofit.Call
import retrofit.Callback
import retrofit.Response
import retrofit.Retrofit


class signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val REQUEST_IMAGE_CAPTURE   = 1
        val GALLERY_REQUEST  = 188
        val MY_CAMERA_PERMISSION_CODE   = 100
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val kolon: ImageView =
            findViewById<View>(R.id.result) as ImageView
        val camera: Button =
            findViewById<View>(R.id.camera) as Button
        val gallery: Button =
            findViewById<View>(R.id.gallery) as Button

        gallery.setOnClickListener {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), MY_CAMERA_PERMISSION_CODE)
            } else {
                val photoPickerIntent = Intent(Intent.ACTION_PICK)
                photoPickerIntent.type = "image/*"
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
            }
        }

        camera.setOnClickListener {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
            } else {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }

        val editTextTextPersonName: EditText =
            findViewById<View>(R.id.editTextTextPersonName) as EditText
        val editTextTextEmailAddress: EditText =
            findViewById<View>(R.id.editTextTextEmailAddress) as EditText
        val editTextPhone: EditText =
            findViewById<View>(R.id.editTextPhone) as EditText
        val editTextTextPassword: EditText =
            findViewById<View>(R.id.editTextTextPassword) as EditText
        val btnSignUp: Button = findViewById<View>(R.id.btnSignUp) as Button

        progress22.visibility=View.GONE

        fun CharSequence?.isValidEmail() = !isNullOrEmpty() && !Patterns.EMAIL_ADDRESS.matcher(this).matches()

        btnSignUp.setOnClickListener {
            val name = editTextTextPersonName.text.toString().trim()
            val email = editTextTextEmailAddress.text.toString().trim()
            val phone = editTextPhone.text.toString().trim()
            val password = editTextTextPassword.text.toString().trim()



            if (name.isEmpty()) {
                Toast.makeText(
                    applicationContext, "Data is missing", Toast.LENGTH_LONG
                ).show()
                editTextTextPersonName.error = "Name required"
                editTextTextPersonName.requestFocus()
                return@setOnClickListener
            } else if (email.isEmpty()) {
                editTextTextEmailAddress.error = "Email required"
                editTextTextEmailAddress.requestFocus()
                return@setOnClickListener
            }
            else if (email.isValidEmail()) {
                editTextTextEmailAddress.error = "Invalid email address"
                editTextTextEmailAddress.requestFocus()
                return@setOnClickListener
            }
            else if (phone.isEmpty()) {
                editTextPhone.error = "Mobile No required"
                editTextPhone.requestFocus()
                return@setOnClickListener
            }
            else if (editTextPhone.getText().toString().length <11 || phone.length >11) {
                editTextPhone.error = "Mobile No Should be 11 Digits"
                editTextPhone.requestFocus()
                return@setOnClickListener
            }
            else if (password.isEmpty()) {
                editTextTextPassword.error = "Password required"
                editTextTextPassword.requestFocus()
                return@setOnClickListener
            }
            else if (editTextTextPassword.text.toString().length <5) {
                editTextTextPassword.error = "Weak Password"
                editTextTextPassword.requestFocus()
                return@setOnClickListener
            }
            else if (email.isEmpty() && name.isEmpty() && phone.isEmpty() && password.isEmpty()) {
                Toast.makeText(
                    applicationContext, "Data is missing!!!", Toast.LENGTH_LONG
                ).show()
            }
            else if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty())
            {
                progress22.visibility=View.VISIBLE
                btnSignUp.visibility=View.GONE
            }

            val apiService = ApiClient.client!!.create(ApiInterface::class.java)
            val call: Call<Signup> = apiService.getSignup(name,email,phone,password)
            call.enqueue(object : Callback<Signup> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(response: Response<Signup>?, retrofit: Retrofit?) {
                    if(response !=null)
                    {
                        progress22.visibility=View.GONE
                        btnSignUp.visibility=View.VISIBLE
                        Toast.makeText(applicationContext, "Successfully Signup", Toast.LENGTH_LONG).show()
                        val intent = Intent(applicationContext, Login::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                        startActivity(intent)
                    }
                    else
                    {
                        progress22.visibility=View.GONE
                        btnSignUp.visibility=View.VISIBLE
                        Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()
                    }

                }

                override fun onFailure(t: Throwable?) {
                    val pp =""

                }

            })
        }
        val sign_in = findViewById<View>(R.id.button3) as Button
        sign_in.setOnClickListener { view ->
            val intent = Intent(view.context, signin::class.java)
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
        val NButton = findViewById<View>(R.id.notify6) as ImageView
        NButton.setOnClickListener { view ->
            val intent = Intent(view.context, notify::class.java)
            view.context.startActivity(intent)
        }
    }

    @SuppressLint("RestrictedApi")
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val REQUEST_IMAGE_CAPTURE = 1
        val GALLERY_REQUEST=10
        val kolon: ImageView =
            findViewById<View>(R.id.result) as ImageView
        if (requestCode == REQUEST_IMAGE_CAPTURE  && resultCode == RESULT_OK) when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                val imageBitmap = data?.extras?.get("data") as Bitmap
                kolon.setImageBitmap(imageBitmap)
            }
            GALLERY_REQUEST -> {
                val Bitmap = data?.extras?.get("data") as Bitmap
                kolon.setImageBitmap(Bitmap)
            }
        }

    }
    override fun onBackPressed() {
        val intent = Intent(applicationContext, NavBar::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)
    }
}