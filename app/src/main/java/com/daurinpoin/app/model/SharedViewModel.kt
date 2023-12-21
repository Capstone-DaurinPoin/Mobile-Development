package com.daurinpoin.app.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daurinpoin.app.repository.UserRepository
import com.daurinpoin.app.response.UserModel
import kotlinx.coroutines.launch

class SharedViewModel(private val userRepository: UserRepository) : ViewModel() {

    val user: MutableLiveData<UserModel?> get() = userRepository.user

    init {
        // Initialize or fetch user data when the ViewModel is created
        // For simplicity, I'm passing a default userId "123" here; replace it as needed
        fetchUser("123")
    }

    fun fetchUser(userId: String) {
        viewModelScope.launch {
            userRepository.getUser(userId)
        }
    }
}
