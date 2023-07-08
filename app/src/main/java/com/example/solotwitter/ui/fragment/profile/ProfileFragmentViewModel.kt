package com.example.solotwitter.ui.fragment.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.solotwitter.db.tweet.TweetRepository
import com.example.solotwitter.db.user.UserRepository

class ProfileFragmentViewModel(
    private val tweetRepository: TweetRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    var tweets = tweetRepository.tweets
    var user = userRepository.currentUser

    val currentUserUsername = MutableLiveData<String>()
    val currentUserHandle = MutableLiveData<String>()

    fun setUser() {
        user = userRepository.currentUser
    }

    fun fetchTweets() {
        tweets = tweetRepository.getTweetsFromUser(user!!.id)
    }
}