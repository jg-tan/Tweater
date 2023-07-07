package com.example.solotwitter.ui.fragment.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.solotwitter.db.tweet.TweetRepository

class FeedFragmentViewModelFactory(private val repository: TweetRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedFragmentViewModel::class.java)) {
            return FeedFragmentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}