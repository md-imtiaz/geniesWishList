package com.shuvostechworld.genieswishlist.views.dashboard.customer.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.shuvostechworld.genieswishlist.R
import com.shuvostechworld.genieswishlist.base.BaseFragment
import com.shuvostechworld.genieswishlist.core.DataState
import com.shuvostechworld.genieswishlist.databinding.FragmentCustomerHomeBinding
import com.shuvostechworld.genieswishlist.db.models.Product
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerHomeFragment : BaseFragment<FragmentCustomerHomeBinding>(FragmentCustomerHomeBinding::inflate) {
    private val viewModel: CustomerProductViewModel by viewModels ()

            override fun setListener() {

    }

    override fun allObservers() {
        viewModel.productResponse.observe(viewLifecycleOwner) {
            when(it) {
                is DataState.Error -> {
                    loading.dismiss()
                }
                is DataState.Loading -> {
                    loading.show()
                }
                is DataState.Success -> {
                    it.data?.let { it1->
                        setDataToRV(it1)
                    }
                    loading.dismiss()
                }
            }
        }

    }
    private fun setDataToRV(it: List<Product>) {
        binding.rvCustomerProduct.adapter = CustomerProductAdapter(it)
    }

}

