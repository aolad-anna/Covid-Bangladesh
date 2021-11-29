package com.example.covidbangladesh

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.covidbangladesh.models.RecyclerAdapter
import com.example.covidbangladesh.api.ApiClient
import com.example.covidbangladesh.api.ApiInterface
import com.example.covidbangladesh.models.Notify
import retrofit.Call
import retrofit.Callback
import retrofit.Response
import retrofit.Retrofit
import kotlinx.android.synthetic.main.activity_notify.*


class notify : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notify)
        progress1000.visibility=View.VISIBLE
        recyclerView = findViewById(R.id.recyclerview)
        recyclerAdapter = RecyclerAdapter(this)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter

        val LButton = findViewById<View>(R.id.logo) as ImageView
        LButton.setOnClickListener { view ->
            val intent = Intent(view.context, NavBar::class.java)
            view.context.startActivity(intent)
        }
        val apiService = ApiClient.client!!.create(ApiInterface::class.java)
        val call: Call<List<Notify>> = apiService.getNotify()
        call.enqueue(object : Callback<List<Notify>> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(response: Response<List<Notify>>?, retrofit: Retrofit?) {

                //if(response?.body() != null)

                if (response != null) {
                    progress1000.visibility=View.GONE
                    recyclerAdapter.setNotifyListItems(response.body().asReversed())
                }
            }

            override fun onFailure(t: Throwable?) {
                val pp =""
            }
        })

    }

}