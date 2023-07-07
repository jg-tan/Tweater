package com.example.solotwitter.db.tweet

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TweetDAO {
    @Insert
    suspend fun insertTweet(tweet: Tweet) : Long

    @Update
    suspend fun updateTweet(tweet: Tweet) : Int

    @Delete
    suspend fun deleteTweet(tweet: Tweet)

    @Query("DELETE FROM tweet_data_table")
    suspend fun deleteAllTweets()

    @Query("SELECT * FROM tweet_data_table")
    fun getAllTweets() : LiveData<List<Tweet>>
}