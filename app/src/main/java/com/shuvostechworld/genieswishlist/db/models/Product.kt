package com.shuvostechworld.genieswishlist.db.models

data class Product(
    var name: String = "",
    var price: Double = 0.0,
    var imageLink: String = "",
    var description: String = "",
    var amount: Int = 0,
    var sellerID: String = "",
    var productID: String = ""
)

