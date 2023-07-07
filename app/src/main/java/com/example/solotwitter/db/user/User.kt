package com.example.solotwitter.db.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val id: Int,

    @ColumnInfo(name = "user_user_name")
    val userName: String,

    @ColumnInfo(name = "user_handle")
    val handle: String,

    @ColumnInfo(name = "user_password")
    val password: String
)
