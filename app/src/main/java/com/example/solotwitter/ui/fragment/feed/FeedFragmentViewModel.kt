package com.example.solotwitter.ui.fragment.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.solotwitter.db.tweet.Tweet
import com.example.solotwitter.db.tweet.TweetRepository
import com.example.solotwitter.db.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedFragmentViewModel(
    private val tweetRepository: TweetRepository, private val userRepository: UserRepository
) : ViewModel() {
    var tweets = tweetRepository.tweets
    var user = userRepository.currentUser

    val tweetContent = MutableLiveData<String>()

    val currentUsernameHandle = MutableLiveData<String>()

    fun setUser() {
        user = userRepository.currentUser
        currentUsernameHandle.value = user!!.userName + "@" + user!!.handle
    }

    fun fetchTweets() {
        tweets = tweetRepository.getTweetsExceptUser(user!!.id)
    }

    fun postTweet() {
        val timestamp = System.currentTimeMillis()
        val content = tweetContent.value!!
        insertTweet(Tweet(0, content, timestamp, user!!.id, user!!.userName, user!!.handle))
        tweetContent.value = ""
    }

    fun insertTweet(tweet: Tweet) = viewModelScope.launch(Dispatchers.IO) {
        tweetRepository.insert(tweet)
    }
}