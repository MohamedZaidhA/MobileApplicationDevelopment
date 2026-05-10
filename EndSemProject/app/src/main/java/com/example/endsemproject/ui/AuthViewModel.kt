package com.example.endsemproject.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.endsemproject.data.User
import com.example.endsemproject.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _userState = MutableStateFlow<User?>(null)
    val userState: StateFlow<User?> = _userState

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun signUp(email: String, pass: String, role: String) {
        viewModelScope.launch {
            val result = repository.signUp(email, pass, role)
            result.onSuccess {
                _userState.value = it
            }.onFailure {
                _error.value = it.message
            }
        }
    }

    fun joinGroup(code: String) {
        val currentUser = _userState.value ?: return
        viewModelScope.launch {
            val result = repository.joinGroup(currentUser.uid, code)
            result.onSuccess {
                _userState.value = repository.getCurrentUser()
            }.onFailure {
                _error.value = it.message
            }
        }
    }
    
    fun initUser() {
        viewModelScope.launch {
            _userState.value = repository.getCurrentUser()
        }
    }
}
