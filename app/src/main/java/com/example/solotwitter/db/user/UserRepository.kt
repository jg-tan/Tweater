package com.example.solotwitter.db.user

class UserRepository(private val dao: UserDAO) {
    var user = null

    suspend fun loginUser(userName: String, password: String) {

    }
}