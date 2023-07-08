package com.example.solotwitter.db.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDAO {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user_data_table WHERE user_user_name == :userName AND user_handle == :handle")
    suspend fun getUser(userName: String, handle: String): User

    @Query("SELECT * FROM user_data_table WHERE user_user_name == :userName AND user_password == :password")
    suspend fun loginUser(userName: String, password: String): User?
}