package com.shuvostechworld.genieswishlist.views.dashboard.customer.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.shuvostechworld.genieswishlist.R
import com.shuvostechworld.genieswishlist.base.BaseFragment
import com.shuvostechworld.genieswishlist.databinding.FragmentCustomerProfileBinding
import com.shuvostechworld.genieswishlist.views.starter.MainActivity

class CustomerProfileFragment : BaseFragment<FragmentCustomerProfileBinding>(
    FragmentCustomerProfileBinding::inflate
) {

    override fun setListener() {
        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        val currentUser = FirebaseAuth.getInstance().currentUser
        binding.tvEmail.text = currentUser?.email?:"Guest User"

    }

    override fun allObservers() {

    }

}

