package com.shuvostechworld.genieswishlist.views.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shuvostechworld.genieswishlist.core.DataState
import com.shuvostechworld.genieswishlist.db.models.UserRegistrationModel
import com.shuvostechworld.genieswishlist.db.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authService: AuthRepository) : ViewModel() {
    private val _loginResponse = MutableLiveData<DataState<UserRegistrationModel>> ()
    val loginResponse: LiveData<DataState<UserRegistrationModel>> = _loginResponse
    fun userLogin(user: UserRegistrationModel) {
        _loginResponse.postValue(   DataState.Loading())
        authService.userLogin(user).addOnSuccessListener { authResult ->
            _loginResponse.postValue(DataState.Success(user))
            Log.d("TAG", "userLogin: Success")
        }.addOnFailureListener { error->
            _loginResponse.postValue(DataState.Error("${error.message}"))
            Log.d("TAG", "User login: ${error.message}")
        }
    }

}

