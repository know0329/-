package com.d208.giggy.utils

import android.content.Context
import android.content.SharedPreferences
import com.d208.giggy.di.App
import com.d208.giggy.utils.Utils.ACCESS_TOKEN


class SharedPreferencesUtil(context: Context) {
    private var preferences: SharedPreferences =
        context.getSharedPreferences(App.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun getString(key: String): String {
        return preferences.getString(key, null) ?: ""
    }
    fun getLong(key: String): Long {
        return preferences.getLong(key, -1)
    }

    fun getInt(key : String) : Int {
        return preferences.getInt(key, 0)
    }

    fun removeUser() {
        preferences.edit().clear().apply()
    }


   fun addId(id : String){
       val editor = preferences.edit()
       editor.putString("id", id)
       editor.apply()
   }
    fun addAccount(account : String){
        val editor = preferences.edit()
        editor.putString("account", account)
        editor.apply()
    }
    fun updateMoney(money : Int){
        val editor = preferences.edit()
        editor.putInt("money", money)
        editor.apply()
    }


    fun addToken(token : String) {
        val editor = preferences.edit()
        editor.putString(ACCESS_TOKEN, token)
        editor.apply()
    }
    fun getToken(): MutableSet<String>? {
        return preferences.getStringSet(ACCESS_TOKEN, HashSet())
    }





}

