package com.example.solotwitter.ui.fragment.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.solotwitter.Event
import com.example.solotwitter.db.user.User
import com.example.solotwitter.db.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragmentViewModel(private val repository: UserRepository) : ViewModel() {

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _navigateToFeed = MutableLiveData<Event<Boolean>>()
    val navigateToFeed: LiveData<Event<Boolean>>
        get() = _navigateToFeed

    fun login() {
        loginUser()
    }

    private fun loginUser() = viewModelScope.launch(Dispatchers.IO) {
        val inputUsername = username.value ?: let {
            Log.i("MyTag", "Username is null")
            return@launch
        }

        val inputPassword = password.value ?: let {
            Log.i("MyTag", "Password is null")
            return@launch
        }

        repository.loginUser(inputUsername, inputPassword) ?: let {
            Log.i("MyTag", "User does not exist")
            return@launch
        }

        withContext(Dispatchers.Main) {
            _navigateToFeed.value = Event(true)
        }
    }
}