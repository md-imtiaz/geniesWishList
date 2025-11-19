package com.shuvostechworld.genieswishlist.db.service

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.QuerySnapshot
import com.shuvostechworld.genieswishlist.db.models.UserRegistrationModel

interface AuthService {
    fun userRegistration(user: UserRegistrationModel): Task<AuthResult>
    fun userLogin(user: UserRegistrationModel): Task<AuthResult>
    fun createUser(user: UserRegistrationModel): Task<Void>
    fun getUserByUserID(userID: String): Task<QuerySnapshot>
}

