package com.shuvostechworld.genieswishlist.views.register

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
class RegistrationViewModel @Inject constructor(
    private val authService: AuthRepository
) : ViewModel() {
    private val _registrationResponse = MutableLiveData<DataState<UserRegistrationModel>>()
    val registrationResponse: LiveData<DataState<UserRegistrationModel>> = _registrationResponse
    fun userRegistration(user: UserRegistrationModel) {
        _registrationResponse.postValue(DataState.Loading())
        authService.userRegistration(user).addOnSuccessListener {
            it.user?.let { createdUser->
                user.userID = createdUser.uid
                authService.createUser(user).addOnSuccessListener {
                    _registrationResponse.postValue(DataState.Success(user))
                    Log.d("TAG", "userRegistration: Success")
                }.addOnFailureListener { error->
                    _registrationResponse.postValue(DataState.Error("${error.message}"))
                    Log.d("TAG", "userRegistration: ${error.message}")
                }
            }


        }.addOnFailureListener {error->
            _registrationResponse.postValue(DataState.Error("${error.message}"))
            Log.d("TAG", "userRegistration: ${error.message}")
        }
    }

}

