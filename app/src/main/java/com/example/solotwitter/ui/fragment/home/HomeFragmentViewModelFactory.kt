package com.example.solotwitter.ui.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.solotwitter.db.user.UserRepository
import com.example.solotwitter.ui.fragment.feed.FeedFragmentViewModel

class HomeFragmentViewModelFactory(private val repository: UserRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeFragmentViewModel::class.java)) {
            return HomeFragmentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}