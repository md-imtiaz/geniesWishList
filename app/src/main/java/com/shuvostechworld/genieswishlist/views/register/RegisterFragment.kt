package com.shuvostechworld.genieswishlist.views.register

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
import com.shuvostechworld.genieswishlist.databinding.FragmentRegisterBinding
import com.shuvostechworld.genieswishlist.db.models.UserRegistrationModel
import com.shuvostechworld.genieswishlist.isEmpty
import com.shuvostechworld.genieswishlist.views.dashboard.customer.CustomerDashboardActivity
import com.shuvostechworld.genieswishlist.views.dashboard.seller.SellerDashboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: RegistrationViewModel by viewModels()

    override fun setListener() {
        with(binding) {
            btnRegister.setOnClickListener{
                val name = etName.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()

                when {
                    name.isEmpty() -> {
                        etName.error = "Name is required"
                    }
                    email.isEmpty() -> {
                        etEmail.error = "Email is required"
                    }
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        etEmail.error = "Invalid email format"
                    }
                    password.isEmpty() -> {
                        etPassword.error = "Password is required"
                    }
                    password.length < 6 -> {
                        etPassword.error = "Password must be at least 6 characters"
                    }
                    else -> {
                        val user = UserRegistrationModel(
                            name,
                            email,
                            password,
                            "Customer",
                            ""
                        )
                        viewModel.userRegistration(user)
                    }
                }
            }

            btnLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

    override fun allObservers() {
        registrationObserver()
    }

    private fun registrationObserver() {
        viewModel.registrationResponse.observe(viewLifecycleOwner) {
            when(it){
                is DataState.Error -> {
                    loading.dismiss()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {
                    loading.show()
//                    Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
                }
                is DataState.Success -> {
                    loading.dismiss()
                    Toast.makeText(context, "Created user: ${it.data}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(requireContext(), CustomerDashboardActivity::class.java))
                    requireActivity().finish()
                }

            }

        }

    }


}


