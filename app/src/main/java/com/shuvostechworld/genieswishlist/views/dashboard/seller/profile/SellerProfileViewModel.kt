package com.shuvostechworld.genieswishlist.views.dashboard.seller.profile

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shuvostechworld.genieswishlist.core.DataState
import com.shuvostechworld.genieswishlist.db.models.Profile
import com.shuvostechworld.genieswishlist.db.repository.SellerProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SellerProfileViewModel @Inject constructor(private val repo: SellerProfileRepository) : ViewModel() {
    private val _profileUpdateResponse = MutableLiveData<DataState<String>>()
    val profileUpdateResponse: LiveData<DataState<String>> = _profileUpdateResponse
    fun updateProfile(user: Profile, hasLocalImage: Boolean) {
        _profileUpdateResponse.postValue(DataState.Loading())
        if (hasLocalImage) {
            val imageUri: Uri? = user.userImage?.toUri()

            imageUri?.let {

                repo.uploadImage(it).addOnSuccessListener { snapshot ->

                    snapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { url ->
                        user.userImage = url.toString()

                        repo.updateUser(user).addOnSuccessListener {
                            _profileUpdateResponse.postValue(DataState.Success("Uploaded and Updated Profile Successfully !"))
                        }.addOnFailureListener {
                            _profileUpdateResponse.postValue(DataState.Error("${it.message}"))
                        }

                    }

                }.addOnFailureListener {
                    _profileUpdateResponse.postValue(DataState.Error("Image Uploading Fail!"))
                }

            }

        } else {
            repo.updateUser(user).addOnSuccessListener {
                _profileUpdateResponse.postValue(DataState.Success("Uploaded and Updated Profile Successfully !"))
            }.addOnFailureListener {
                _profileUpdateResponse.postValue(DataState.Error("${it.message}"))
            }

        }
        }

    private val _logedInUserResponse = MutableLiveData<DataState<Profile>>()
    val logedInUserResponse: LiveData<DataState<Profile>> = _logedInUserResponse

    fun getUserByUserID(userID: String) {
        _logedInUserResponse.postValue(DataState.Loading())
        repo.getUserByUserID(userID).addOnSuccessListener { value->
            _logedInUserResponse.postValue(DataState.Success(value.documents[0].toObject(Profile::class.java)))
        }
    }

    }

