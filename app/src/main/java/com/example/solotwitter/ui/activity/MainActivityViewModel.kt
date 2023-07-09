package com.example.solotwitter.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.solotwitter.AppSharedPref
import com.example.solotwitter.db.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(private val userRepository: UserRepository): ViewModel() {

    fun setCurrentUser() = viewModelScope.launch(Dispatchers.IO) {
        userRepository.setCurrentUserById(AppSharedPref.getLoggedInUserId())
    }
}