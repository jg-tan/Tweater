package com.example.solotwitter.db.tweet

import androidx.lifecycle.LiveData

class TweetRepository(private val dao: TweetDAO) {
    var tweets: LiveData<List<Tweet>>? = null
    var tweetsFromUser: LiveData<List<Tweet>>? = null

    suspend fun insert(tweet: Tweet) {
        dao.insertTweet(tweet)
    }

    fun getTweetsFromUser(userId: Int): LiveData<List<Tweet>>? {
        tweets = dao.getAllTweetsFromUser(userId)
        return tweets
    }

    fun getTweetsExceptUser(userId: Int): LiveData<List<Tweet>>? {
        tweets = dao.getAllTweetsExceptUser(userId)
        return tweets
    }
}