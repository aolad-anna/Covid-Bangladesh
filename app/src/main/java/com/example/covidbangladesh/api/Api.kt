package com.example.covidbangladesh.api

import com.example.covidbangladesh.models.*
import retrofit.Call
import retrofit.http.Field
import retrofit.http.GET
import retrofit.http.POST
import retrofit.http.Query


interface ApiInterface {

    @GET("api.php")
    fun getMovies() : Call<List<Movie>>
    @GET("api2.php")
    fun getVaccine() : Call<List<Vaccine>>
    @GET("notify_api.php")
    fun getNotify() : Call<List<Notify>>
//
//    @GET("notify_api.php")
//    fun getNotify(
////        @Query("ndate") ndate: String?,
////        @Query("details") details: String?
//    ): Call<Notify>


    @GET("login.php")
    fun getLogin(
        @Query("email") email: String?,
        @Query("password") password: String?
    ): Call<Login>

    @POST("reg_api.php")
    fun getSignup(
        @Query("name") name: String?,
        @Query("email") email: String?,
        @Query("phone") phone: String?,
        @Query("password") password: String?
    ): Call<Signup>

}