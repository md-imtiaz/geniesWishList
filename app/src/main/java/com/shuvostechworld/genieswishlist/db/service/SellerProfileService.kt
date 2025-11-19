package com.shuvostechworld.genieswishlist.db.service

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.UploadTask
import com.shuvostechworld.genieswishlist.db.models.Profile

interface SellerProfileService {
    fun uploadImage(productImageUri: Uri): UploadTask
    fun updateUser(user: Profile): Task<Void>
    fun getUserByUserID(userID: String): Task<QuerySnapshot>
}

