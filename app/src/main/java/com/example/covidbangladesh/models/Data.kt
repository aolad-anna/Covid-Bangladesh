package com.example.covidbangladesh.models

import com.google.gson.annotations.SerializedName

data class Data(

    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("password") val password: String?,
    @SerializedName("created_at") val created_at: String?

)

//data class data(val id:Int, val name:String, val email:String, val phone:String, val password:String, val created_at:String)