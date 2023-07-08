package com.example.solotwitter.ui.fragment.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.solotwitter.db.tweet.TweetRepository
import com.example.solotwitter.db.user.UserRepository

class ProfileFragmentViewModelFactory(
    private val tweetRepository: TweetRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileFragmentViewModel::class.java)) {
            return ProfileFragmentViewModel(tweetRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}