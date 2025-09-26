package com.shuvostechworld.genieswishlist

import android.widget.EditText

fun EditText.isEmpty(): Boolean {
    return if (this.text.toString().isEmpty()) {
        this.error = "This field need to be filled up "
        true
    } else {
        false
    }

}

