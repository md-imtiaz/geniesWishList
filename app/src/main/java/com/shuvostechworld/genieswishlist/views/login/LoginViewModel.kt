package com.shuvostechworld.genieswishlist.views.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult
import com.shuvostechworld.genieswishlist.core.DataState
import com.shuvostechworld.genieswishlist.db.models.UserRegistrationModel
import com.shuvostechworld.genieswishlist.db.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authService: AuthRepository) : ViewModel() {
    private val _loginResponse = MutableLiveData<DataState<AuthResult>> ()
    val loginResponse: LiveData<DataState<AuthResult>> = _loginResponse
    private val _userRoleResponse = MutableLiveData<DataState<String>>()
    val userRoleResponse: LiveData<DataState<String>> = _userRoleResponse
    fun userLogin(user: UserRegistrationModel) {
        _loginResponse.postValue(   DataState.Loading())
        authService.userLogin(user).addOnSuccessListener { authResult ->
            _loginResponse.postValue(DataState.Success(authResult))
            Log.d("TAG", "userLogin: Success")
        }.addOnFailureListener { error->
            _loginResponse.postValue(DataState.Error("${error.message}"))
            Log.d("TAG", "User login: ${error.message}")
        }
    }

    fun checkUserRole(userID: String) {
        _userRoleResponse.postValue(DataState.Loading())
        authService.getUserByUserID(userID).addOnSuccessListener { document ->

            if (document.exists()) {
                val userInfo = document.toObject(UserRegistrationModel::class.java)
                userInfo?.let {
                    _userRoleResponse.postValue(DataState.Success(it.userRole))
                }

            } else {
                _userRoleResponse.postValue(DataState.Error("User data not found!"))
            }

        }.addOnFailureListener { error->
            _userRoleResponse.postValue(DataState.Error("${error.message}"))
        }

    }


}

