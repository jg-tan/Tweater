package com.example.solotwitter.ui.fragment.signup

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

class SignupFragmentViewModel(private val repository: UserRepository) : ViewModel() {
    val username = MutableLiveData<String>()
    val handle = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordConfirm = MutableLiveData<String>()

    private val _navigateToFeed = MutableLiveData<Event<Boolean>>()
    val navigateToFeed: LiveData<Event<Boolean>>
        get() = _navigateToFeed

    private val _navigateToHome = MutableLiveData<Event<Boolean>>()
    val navigateToHome: LiveData<Event<Boolean>>
        get() = _navigateToHome

    fun signUp() {
        signUpUser()
    }

    fun onBackClicked() {
        _navigateToHome.value = Event(true)
    }

    private fun signUpUser() = viewModelScope.launch(Dispatchers.IO) {
        val inputUsername = username.value ?: let {
            Log.i("MyTag", "Username is null")
            return@launch
        }
        val inputHandle = handle.value ?: let {
            Log.i("MyTag", "Username is null")
            return@launch
        }
        val inputPassword = password.value ?: let {
            Log.i("MyTag", "Password is null")
            return@launch
        }
        val inputPasswordConfirm = passwordConfirm.value ?: let {
            Log.i("MyTag", "Password does not match")
            return@launch
        }

        if (inputPassword != inputPasswordConfirm) {
            Log.i("MyTag", "Password does not match")
            return@launch
        }

        if (repository.isUserExist(inputUsername, inputHandle)) {
            Log.i("MyTag", "Username exists")
            return@launch
        }

        repository.createUser(User(0, inputUsername, inputHandle, inputPassword))

        withContext(Dispatchers.Main) {
            _navigateToFeed.value = Event(true)
        }
    }


}