package com.example.covidbangladesh.models

import com.google.gson.annotations.SerializedName

data class Notify(

	@SerializedName("id") val id : Int,
	@SerializedName("ndate") val ndate : String,
	@SerializedName("details") val details : String,
	@SerializedName("updated_at") val updated_at : String

)

//data class notify(var id: String, var ndate: String,var details: String,var updated_at: String)