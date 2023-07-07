package com.example.solotwitter.db.tweet

class TweetRepository(private val dao: TweetDAO) {
    val tweets = dao.getAllTweets()

    suspend fun insert(tweet: Tweet) {
        dao.insertTweet(tweet)
    }

    suspend fun update(tweet: Tweet) {
        dao.updateTweet(tweet)
    }

    suspend fun delete(tweet: Tweet) {
        dao.deleteTweet(tweet)
    }

    suspend fun clearAll() {
        dao.deleteAllTweets()
    }
}