package com.shuvostechworld.genieswishlist.views.starter

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.shuvostechworld.genieswishlist.R
import com.shuvostechworld.genieswishlist.base.BaseFragment
import com.shuvostechworld.genieswishlist.core.DataState
import com.shuvostechworld.genieswishlist.databinding.FragmentStartBinding
import com.shuvostechworld.genieswishlist.views.dashboard.customer.CustomerDashboardActivity
import com.shuvostechworld.genieswishlist.views.dashboard.seller.SellerDashboard
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class StartFragment : BaseFragment<FragmentStartBinding> (FragmentStartBinding::inflate){
    private val viewModel: StartViewModel by viewModels ()

    override fun setListener() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let {
            viewModel.checkUserRole(it.uid)
            // lets brace
        }
        with(binding) {
            btnLogin.setOnClickListener { findNavController().navigate(R.id.action_startFragment_to_loginFragment) }
            btnRegister.setOnClickListener { findNavController().navigate(R.id.action_startFragment_to_registerFragment) }
            // withs brace
        }
            // funcs brace
    }

    private fun setupAutoLogin () {
        FirebaseAuth.getInstance().currentUser?.let {
            startActivity(Intent(requireContext(), CustomerDashboardActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun allObservers() {
        viewModel.userRoleResponse.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    loading.show()
                }
                is DataState.Error -> {
                    loading.dismiss()
                    FirebaseAuth.getInstance().signOut()
                    Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }
                is DataState.Success -> {
                    loading.dismiss()
                    if (it.data == "Seller") {
                        startActivity(Intent(requireContext(), SellerDashboard::class.java))
                        // ifs brace
                    } else {
                        startActivity(Intent(requireContext(), CustomerDashboardActivity::class.java))
                        // elses brace
                    }
                    requireActivity().finish()
                    // successes brace
                }
                // whens brace
            }
            // observers brace
        }
        // funcs brace
    }

}

