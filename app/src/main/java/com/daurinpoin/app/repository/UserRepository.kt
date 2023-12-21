package com.daurinpoin.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.daurinpoin.app.response.UserModel
import com.daurinpoin.app.service.ApiService

class UserRepository(private val apiService: ApiService) {

    private val _user = MutableLiveData<UserModel?>()
    val user: MutableLiveData<UserModel?> get() = _user

    suspend fun getUser(userId: String) {
        try {
            val response = apiService.getUser(userId)
            val user = response.data
            _user.postValue(user)
        } catch (e: Exception) {
            // Handle the error
        }
    }
}

