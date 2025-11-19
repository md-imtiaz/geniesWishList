package com.shuvostechworld.genieswishlist.views.starter

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.shuvostechworld.genieswishlist.R
import com.shuvostechworld.genieswishlist.base.BaseFragment
import com.shuvostechworld.genieswishlist.databinding.FragmentStartBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StartFragment : BaseFragment<FragmentStartBinding> (FragmentStartBinding::inflate){
    override fun setListener() {
        setupAutoLogin()
        with(binding) {
            binding.btnLogin.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_loginFragment)
            }
            binding.btnRegister.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_registerFragment)
            }
        }

    }

    private fun setupAutoLogin () {
        FirebaseAuth.getInstance().currentUser?.let {
            findNavController().navigate(R.id.action_startFragment_to_dashboardFragment)
        }
    }

    override fun allObservers() {

    }

}

