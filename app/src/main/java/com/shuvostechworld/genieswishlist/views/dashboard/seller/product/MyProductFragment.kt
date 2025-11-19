package com.shuvostechworld.genieswishlist.views.dashboard.seller.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.shuvostechworld.genieswishlist.R
import com.shuvostechworld.genieswishlist.base.BaseFragment
import com.shuvostechworld.genieswishlist.core.DataState
import com.shuvostechworld.genieswishlist.databinding.FragmentMyProductBinding
import com.shuvostechworld.genieswishlist.db.models.Product
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProductFragment: BaseFragment<FragmentMyProductBinding>(FragmentMyProductBinding::inflate) {
    private val viewModel: ProductViewModel by viewModels()
    override fun setListener() {
        FirebaseAuth.getInstance().currentUser?.let {
            viewModel.getProductByID(it.uid)
        }
    }

    override fun allObservers() {
        viewModel.productResponse.observe(viewLifecycleOwner){
            when (it) {
                is DataState.Error -> {
                    loading.dismiss()
                }
                is DataState.Loading-> {
                    loading.show()
                }
                is DataState.Success-> {
                    it.data?.let { it1->
                        setDataToRV(it1)
                    }
                    loading.dismiss()
                }
            }
        }
    }

    private fun setDataToRV(productList: List<Product>) {
        binding.rvSeller.adapter = SellerProductAdapter(productList)
    }
}

