package com.shuvostechworld.genieswishlist.views.dashboard.customer.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shuvostechworld.genieswishlist.core.DataState
import com.shuvostechworld.genieswishlist.db.models.Product
import com.shuvostechworld.genieswishlist.db.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CustomerProductViewModel @Inject constructor(private val repo: CustomerRepository) : ViewModel() {
    private val _productResponse = MutableLiveData<DataState<List<Product>>>()
    val productResponse: LiveData<DataState<List<Product>>> = _productResponse
    init {
        getAllProduct()
    }
    fun getAllProduct() {
        _productResponse.postValue(DataState.Loading())
        repo.getAllProduct().addOnSuccessListener { document->
            val productList = mutableListOf<Product>()
            document.documents.forEach { doc->

                doc.toObject(Product::class.java)?.let {
                    productList.add(it)
                }

            }
            _productResponse.postValue(DataState.Success(productList))

        }.addOnFailureListener {
            _productResponse.postValue(DataState.Error("${it.message}"))
        }

    }


}

