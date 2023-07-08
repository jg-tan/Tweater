package com.example.solotwitter.db.user

import android.util.Log

class UserRepository(private val dao: UserDAO) {
    var currentUser : User? = User(0, "", "", "")

    suspend fun loginUser(userName: String, password: String): User? {
        currentUser = dao.loginUser(userName, password)
        Log.i("MyTag", "@loginUser : ${currentUser?.userName} @ ${currentUser?.handle}")
        return currentUser
    }

    suspend fun isUserExist(userName: String, handle: String): Boolean {
        return dao.getUser(userName, handle) != null
    }

    suspend fun createUser(user: User) {
        dao.insertUser(user)
        currentUser = dao.getUser(user.userName, user.handle)
        Log.i("MyTag", "@createUser : ${currentUser?.userName} @ ${currentUser?.handle}")
    }
}