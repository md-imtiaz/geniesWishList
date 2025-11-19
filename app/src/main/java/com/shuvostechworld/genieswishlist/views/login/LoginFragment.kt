package com.shuvostechworld.genieswishlist.views.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shuvostechworld.genieswishlist.R
import com.shuvostechworld.genieswishlist.base.BaseFragment
import com.shuvostechworld.genieswishlist.core.DataState
import com.shuvostechworld.genieswishlist.databinding.FragmentLoginBinding
import com.shuvostechworld.genieswishlist.db.models.UserRegistrationModel
import com.shuvostechworld.genieswishlist.isEmpty
import com.shuvostechworld.genieswishlist.views.dashboard.seller.SellerDashboard
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()
    override fun setListener() {
        with(binding) {
            btnLogin.setOnClickListener {
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()
                when {
                    email.isEmpty() -> {
                        etEmail.error = "Email is required"
                    }
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        etEmail.error = "Invalid email format"
                    }
                    password.isEmpty() -> {
                        etPassword.error = "Password is required"
                    }
                    else -> {
                        val user = UserRegistrationModel(
                            userName = "",
                            userEmail = email,
                            userPassword = password,
                            userRole = "",
                            userID = ""
                        )
                        viewModel.userLogin(user)
                    }

                }


            }
            btnRegister.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_registerFragment) }
        }


    }

    override fun allObservers() {
        loginObserver()
    }

    private fun loginObserver() {
        viewModel.loginResponse.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Error -> {
                    loading.dismiss()
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                is DataState.Loading -> {
                    loading.show()
//                    Toast.makeText(context, "Logging in...", Toast.LENGTH_SHORT).show()
                }
                is DataState.Success -> {
                    loading.dismiss()
                    Toast.makeText(context, "Welcome", Toast.LENGTH_LONG).show()
                    startActivity(Intent(requireContext(), SellerDashboard::class.java))
                    requireActivity().finish()
                }
            }
        }
    }

}

