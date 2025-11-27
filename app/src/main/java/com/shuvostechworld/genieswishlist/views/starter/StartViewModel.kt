package com.shuvostechworld.genieswishlist.views.starter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shuvostechworld.genieswishlist.core.DataState
import com.shuvostechworld.genieswishlist.db.models.UserRegistrationModel
import com.shuvostechworld.genieswishlist.db.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(private val repo: AuthRepository) : ViewModel(){
    private val _userRoleResponse = MutableLiveData<DataState<String>>()
    val userRoleResponse: LiveData<DataState<String>> = _userRoleResponse
    fun checkUserRole(userID: String) {
        _userRoleResponse.postValue(DataState.Loading())
        repo.getUserByUserID(userID).addOnSuccessListener { document ->
            if (document.exists()) {
                val user = document.toObject(UserRegistrationModel::class.java)
                user?.let {
                    _userRoleResponse.postValue(DataState.Success(it.userRole))
                    // lets brace
                }
                // ifs brace
            } else {
                _userRoleResponse.postValue(DataState.Error("User not found"))
                // elses brace
            }
            // SuccessListeners brace
        }.addOnFailureListener {
            _userRoleResponse.postValue(DataState.Error(it.message.toString()))
            // FailureListeners brace
        }
        // funcs brace
    }
    // classes brace

}

