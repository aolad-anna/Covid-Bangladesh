package com.example.covidbangladesh.api

import retrofit.GsonConverterFactory
import retrofit.Retrofit


object ApiClient {
    const val BASE_URL = "https://aoladanna.info/"
    private var retrofit: Retrofit? = null
    val client: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
}
