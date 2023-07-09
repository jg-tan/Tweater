package com.example.solotwitter

import android.content.Context

object AppSharedPref {
    private val LOGGED_IN_USER_ID = "logged_in_user_id"

    private val sharedPref = MainApplication.getContext().getSharedPreferences("tweater", Context.MODE_PRIVATE)

    fun setLoggedInUserId(userId : Int) {
        sharedPref.edit().putInt(LOGGED_IN_USER_ID, userId).apply()
    }

    fun getLoggedInUserId() : Int {
        return sharedPref.getInt(LOGGED_IN_USER_ID, -1)
    }
}