package com.example.covidbangladesh.storage

import android.annotation.SuppressLint
import android.content.Context
import com.example.covidbangladesh.models.Data


class SharedPrefManager private constructor(private val mCtx: Context) {

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getInt("id", -1) != -1
        }

    val data: Data
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return Data(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("phone", null),
                sharedPreferences.getString("password", null),
                sharedPreferences.getString("created_at", null)

            )
        }


    fun saveUser(data: List<Data>) {

        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("id", data[0].id)
        editor.putString("name", data[0].name)
        editor.putString("email", data[0].email)
        editor.putString("phone", data[0].phone)
        editor.putString("password", data[0].password)
        editor.putString("created_at", data[0].created_at)

        editor.apply()

    }

    fun clear(data: Any) {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }


    companion object {
        val SHARED_PREF_NAME = "my_shared_preff"
        @SuppressLint("StaticFieldLeak")
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }

}