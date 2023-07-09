package com.example.solotwitter.ui.fragment.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.solotwitter.AppSharedPref
import com.example.solotwitter.Event
import com.example.solotwitter.db.user.User
import com.example.solotwitter.db.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class SignupFragmentEvents {
    NAVIGATE_TO_FEED,
    NAVIGATE_TO_HOME
}

class SignupFragmentViewModel(private val repository: UserRepository) : ViewModel() {
    val username = MutableLiveData<String>()
    val handle = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordConfirm = MutableLiveData<String>()

    private val _eventHandler = MutableLiveData<Event<SignupFragmentEvents>>()
    val eventHandler: LiveData<Event<SignupFragmentEvents>>
        get() = _eventHandler

    private val _messageHandler = MutableLiveData<Event<String>> ()
    val messageHandler: LiveData<Event<String>>
        get() = _messageHandler

    fun signUp() {
        signUpUser()
    }

    fun onBackClicked() {
        _eventHandler.value = Event(SignupFragmentEvents.NAVIGATE_TO_HOME)
    }

    private fun signUpUser() = viewModelScope.launch(Dispatchers.IO) {
        val inputUsername = username.value ?: let {
            Log.i("MyTag", "Username is null")
            sendToastMessage("Please enter a username")
            return@launch
        }

        val inputHandle = handle.value ?: let {
            Log.i("MyTag", "Handle is null")
            sendToastMessage("Please enter a Tweater handle")
            return@launch
        }

        val inputPassword = password.value ?: let {
            Log.i("MyTag", "Password is null")
            sendToastMessage("Please enter a password")
            return@launch
        }

        val inputPasswordConfirm = passwordConfirm.value ?: let {
            Log.i("MyTag", "Password does not match")
            sendToastMessage("Password does not match")
            return@launch
        }

        if (inputPassword != inputPasswordConfirm) {
            Log.i("MyTag", "Password does not match")
            sendToastMessage("Password does not match")
            return@launch
        }

        if (repository.isUserExist(inputUsername, inputHandle)) {
            Log.i("MyTag", "Username exists")
            sendToastMessage("Username exists")
            return@launch
        }

        repository.createUser(User(0, inputUsername, inputHandle, inputPassword))
        repository.currentUser?.let { user ->
            AppSharedPref.setLoggedInUserId(user.id)
            sendToastMessage("User created successfully! Welcome to Tweater ${user.userName}!")
        }

        withContext(Dispatchers.Main) {
            _eventHandler.value = Event(SignupFragmentEvents.NAVIGATE_TO_FEED)
        }
    }

    private suspend fun sendToastMessage(message: String) {
        withContext(Dispatchers.Main) {
            _messageHandler.value = Event(message)
        }
    }
}