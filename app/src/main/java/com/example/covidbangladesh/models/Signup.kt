package com.example.covidbangladesh.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Signup(

   @SerializedName("name")
   @Expose
   val name: String?,

   @SerializedName("email")
   @Expose
   val email: String?,

   @SerializedName("phone")
   @Expose
   val phone: String?,

   @SerializedName("password")
   @Expose
   val password: String?,

//   @SerializedName("c_password")
//   @Expose
//   val c_password: String?,
//
//   @SerializedName("created_at")
//   @Expose
//   val created_at: String?


)