package com.shuvostechworld.genieswishlist.views.dashboard.customer.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shuvostechworld.genieswishlist.databinding.ItemProductBinding
import com.shuvostechworld.genieswishlist.db.models.Product

class CustomerProductAdapter(val productList: List<Product>) : RecyclerView.Adapter<CustomerProductAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        return ProductViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        productList[position].let {
            holder.binding.apply {
                txtProductName.text = it.name
                txtProductDescription.text = it.description
                txtProductPrice.text = "Price: $ ${it.price}"
                txtProductStock.text = "Stock: ${it.amount}"
                ivProduct.load(it.imageLink)
            }

        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)
}
