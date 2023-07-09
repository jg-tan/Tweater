package com.example.solotwitter.ui.fragment.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.solotwitter.AppSharedPref
import com.example.solotwitter.Event
import com.example.solotwitter.db.tweet.TweetRepository
import com.example.solotwitter.db.user.UserRepository

enum class ProfileFragmentEvents {
    NAVIGATE_TO_HOME
}

class ProfileFragmentViewModel(
    private val tweetRepository: TweetRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    var tweets = tweetRepository.tweets
    var user = userRepository.currentUser
    var selectedUser = userRepository.selectedUser

    val currentUserUsername = MutableLiveData<String>()
    val currentUserHandle = MutableLiveData<String>()

    private val _eventHandler = MutableLiveData<Event<ProfileFragmentEvents>>()
    val eventHandler: LiveData<Event<ProfileFragmentEvents>>
        get() = _eventHandler

    fun setUser() {
        if (selectedUser != null) {
            currentUserUsername.value = selectedUser!!.userName
            currentUserHandle.value = selectedUser!!.handle
        } else {
            user = userRepository.currentUser
            currentUserUsername.value = user!!.userName
            currentUserHandle.value = user!!.handle
        }

    }

    fun fetchTweets() {
        if (selectedUser != null) {
            tweets = tweetRepository.getTweetsFromUser(selectedUser!!.id)
        } else {
            tweets = tweetRepository.getTweetsFromUser(user!!.id)
        }
    }

    fun onLogoutClicked() {
        AppSharedPref.setLoggedInUserId(-1)
        _eventHandler.value = Event(ProfileFragmentEvents.NAVIGATE_TO_HOME)
    }
}