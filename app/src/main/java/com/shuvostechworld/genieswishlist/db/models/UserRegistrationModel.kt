package com.shuvostechworld.genieswishlist.db.models

data class UserRegistrationModel(
    val userName: String,
    val userEmail: String,
    val userPassword: String,
    val userRole: String,
    var userID: String

)


