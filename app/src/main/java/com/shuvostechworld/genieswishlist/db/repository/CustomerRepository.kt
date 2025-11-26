package com.shuvostechworld.genieswishlist.db.repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.shuvostechworld.genieswishlist.core.Nodes
import com.shuvostechworld.genieswishlist.db.models.Product
import com.shuvostechworld.genieswishlist.db.service.CustomerService
import javax.inject.Inject

class CustomerRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val storageRef: StorageReference
): CustomerService {
    override fun uploadProductImage(productImageUri: Uri): UploadTask {
        val storage: StorageReference = storageRef.child("product").child("PRD_${System.currentTimeMillis()}")
        return storage.putFile(productImageUri)
    }

    override fun uploadProduct(product: Product): Task<Void> {
        return db.collection(Nodes.PRODUCT).document(product.productID).set(product)
    }

    override fun getAllProductByUserID(userID: String): Task<QuerySnapshot> {
        return db.collection(Nodes.PRODUCT).whereEqualTo("sellerID", userID).get()
    }

    override fun getAllProduct(): Task<QuerySnapshot> {
        return db.collection(Nodes.PRODUCT).get()
    }

}

