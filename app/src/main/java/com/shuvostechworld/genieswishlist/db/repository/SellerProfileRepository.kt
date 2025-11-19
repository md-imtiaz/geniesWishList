package com.shuvostechworld.genieswishlist.db.repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.shuvostechworld.genieswishlist.core.Nodes
import com.shuvostechworld.genieswishlist.db.models.Profile
import com.shuvostechworld.genieswishlist.db.models.toMap
import com.shuvostechworld.genieswishlist.db.service.SellerProfileService
import javax.inject.Inject

class SellerProfileRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val storageRef: StorageReference
): SellerProfileService {
    override fun uploadImage(productImageUri: Uri): UploadTask {
        val storage: StorageReference = storageRef.child("profile").child("USER_${System.currentTimeMillis()}")
        return storage.putFile(productImageUri)
    }

    override fun updateUser(user: Profile): Task<Void> {
        return db.collection(Nodes.USER).document(user.userID).update(user.toMap())
    }

    override fun getUserByUserID(userID: String): Task<QuerySnapshot> {
        return db.collection(Nodes.USER).whereEqualTo("userID", userID).get()
    }
}

