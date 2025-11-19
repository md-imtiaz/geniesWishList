package com.shuvostechworld.genieswishlist.db.service

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.UploadTask
import com.shuvostechworld.genieswishlist.db.models.Product

interface SellerService {
    fun uploadProductImage(productImageUri: Uri): UploadTask
    fun uploadProduct(product: Product): Task<Void>
    fun getAllProductByUserID(userID: String): Task<QuerySnapshot>
}

