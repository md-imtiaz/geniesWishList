package com.shuvostechworld.genieswishlist.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.shuvostechworld.genieswishlist.db.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {
    @Provides
    @Singleton
    fun providesFirebaseAuth() : FirebaseAuth{
        return FirebaseAuth.getInstance()
    }
    @Provides
    @Singleton
    fun providesFirebaseFireStoreDB(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
    @Provides
    @Singleton
    fun providesFirebaseStorage (): StorageReference {
        return FirebaseStorage.getInstance().reference
    }
    @Provides
    @Singleton
    fun providesFirebase(gAuth: FirebaseAuth, db: FirebaseFirestore): AuthRepository{
        return AuthRepository(gAuth, db)
    }

}

