package com.example.solotwitter.db.tweet

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tweet_data_table")
data class Tweet(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tweet_id")
    val id: Int,

    @ColumnInfo(name = "tweet_content")
    val content: String,

    @ColumnInfo(name = "tweet_timestamp")
    val timestamp: Long,

    @ColumnInfo(name = "tweet_user_id")
    val user_id: Int = 0,

    @ColumnInfo(name = "tweet_user_username")
    val user_username: String,

    @ColumnInfo(name = "tweet_user_handle")
    val user_handle: String,
)