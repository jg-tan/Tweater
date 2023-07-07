package com.example.solotwitter.ui.fragment.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.solotwitter.db.tweet.Tweet
import com.example.solotwitter.db.tweet.TweetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedFragmentViewModel(private val repository: TweetRepository) : ViewModel() {
    val tweets = repository.tweets

    val tweetContent = MutableLiveData<String>()

    fun postTweet() {
        val timestamp = System.currentTimeMillis()
        val content = tweetContent.value!!
        insertTweet(Tweet(0, content, timestamp))
    }

    fun insertTweet(tweet: Tweet) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(tweet)
    }
}