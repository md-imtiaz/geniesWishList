package com.shuvostechworld.genieswishlist.views.dashboard.seller.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.shuvostechworld.genieswishlist.databinding.ItemSellerProductBinding
import com.shuvostechworld.genieswishlist.db.models.Product

class SellerProductAdapter (val productList: List<Product>) : RecyclerView.Adapter<SellerProductAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemSellerProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        productList[position].let {
            holder.binding.apply {
                tvProductName.text = it.name
                tvDescription.text = it.description
                tvProductPrice.text = "Price: $${it.price}"
                tvProductStock.text = "Stock: ${it.amount}"
                ivProduct.load(it.imageLink)
            }
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
    class ProductViewHolder(val binding: ItemSellerProductBinding) : ViewHolder(binding.root)
}

