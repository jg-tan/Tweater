package com.example.solotwitter.db

import com.example.solotwitter.MainApplication
import com.example.solotwitter.db.tweet.TweetDatabase
import com.example.solotwitter.db.tweet.TweetRepository
import com.example.solotwitter.db.user.UserDatabase
import com.example.solotwitter.db.user.UserRepository

object RepositoryProvider {
    var tweetRepository = TweetRepository(TweetDatabase.getInstance(MainApplication.getContext()).tweetDAO)
    var userRepository = UserRepository(UserDatabase.getInstance(MainApplication.getContext()).userDAO)
}