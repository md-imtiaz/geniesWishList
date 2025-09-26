package com.shuvostechworld.genieswishlist.views.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.shuvostechworld.genieswishlist.R
import com.shuvostechworld.genieswishlist.databinding.FragmentLoginBinding
import com.shuvostechworld.genieswishlist.databinding.FragmentRegisterBinding
import com.shuvostechworld.genieswishlist.isEmpty

class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        setListener()
        return binding.root
    }
    private fun setListener() {
        with(binding) {
            btnRegister.setOnClickListener {
                etName.isEmpty()
                etEmail.isEmpty()
                etPassword.isEmpty()

                if (!etName.isEmpty() && !etEmail.isEmpty() && !etPassword.isEmpty()) {
                    Toast.makeText(context, "All input done!", Toast.LENGTH_LONG).show()
                }


            }
            btnLogin.setOnClickListener { findNavController().navigate(R.id.action_registerFragment_to_loginFragment) }
        }

    }

}


