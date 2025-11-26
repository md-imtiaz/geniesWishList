package com.shuvostechworld.genieswishlist.db.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.shuvostechworld.genieswishlist.core.Nodes
import com.shuvostechworld.genieswishlist.db.models.UserRegistrationModel
import com.shuvostechworld.genieswishlist.db.service.AuthService
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val gAuth: FirebaseAuth,
    private val db: FirebaseFirestore
) : AuthService{
    override fun userRegistration(user: UserRegistrationModel): Task<AuthResult> {
        return gAuth.createUserWithEmailAndPassword(user.userEmail, user.userPassword)
    }

    override fun userLogin(user: UserRegistrationModel): Task<AuthResult> {
        return gAuth.signInWithEmailAndPassword(user.userEmail, user.userPassword)
    }

    override fun createUser(user: UserRegistrationModel): Task<Void> {
        return db.collection(Nodes.USER).document(user.userID).set(user)
    }

    override fun getUserByUserID(userID: String): Task<DocumentSnapshot> {
        return db.collection(Nodes.USER).document(userID).get()
    }
}

