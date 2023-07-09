package com.example.solotwitter.ui.fragment.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.solotwitter.AppSharedPref
import com.example.solotwitter.Event
import com.example.solotwitter.db.user.User
import com.example.solotwitter.db.user.UserRepository
import com.example.solotwitter.ui.fragment.signup.SignupFragmentEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class HomeFragmentEvents {
    NAVIGATE_TO_FEED,
    NAVIGATE_TO_SIGNUP
}

class HomeFragmentViewModel(private val repository: UserRepository) : ViewModel() {

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _eventHandler = MutableLiveData<Event<HomeFragmentEvents>>()
    val eventHandler: LiveData<Event<HomeFragmentEvents>>
        get() = _eventHandler

    private val _messageHandler = MutableLiveData<Event<String>>()
    val messageHandler: LiveData<Event<String>>
        get() = _messageHandler

    fun login() {
        loginUser()
    }

    private fun loginUser() = viewModelScope.launch(Dispatchers.IO) {
        val inputUsername = username.value ?: let {
            Log.i("MyTag", "Username is null")
            sendToastMessage("Incorrect username or password")
            return@launch
        }

        val inputPassword = password.value ?: let {
            Log.i("MyTag", "Password is null")
            sendToastMessage("Incorrect username or password")
            return@launch
        }

        repository.loginUser(inputUsername, inputPassword) ?: let {
            Log.i("MyTag", "User does not exist")
            sendToastMessage("Incorrect username or password")
            return@launch
        }

        repository.currentUser?.let { user ->
            AppSharedPref.setLoggedInUserId(user.id)
            sendToastMessage("Logged in successfully! Welcome ${user.userName}")
        }

        withContext(Dispatchers.Main) {
            _eventHandler.value = Event(HomeFragmentEvents.NAVIGATE_TO_FEED)
        }
    }

    private suspend fun sendToastMessage(message: String) {
        withContext(Dispatchers.Main) {
            _messageHandler.value = Event(message)
        }
    }

    fun onSignupClicked() {
        _eventHandler.value = Event(HomeFragmentEvents.NAVIGATE_TO_SIGNUP)
    }
}