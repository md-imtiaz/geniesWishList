package com.shuvostechworld.genieswishlist.views.dashboard.seller.upload

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.shuvostechworld.genieswishlist.R
import com.shuvostechworld.genieswishlist.base.BaseFragment
import com.shuvostechworld.genieswishlist.core.DataState
import com.shuvostechworld.genieswishlist.core.areAllPermissionGranted
import com.shuvostechworld.genieswishlist.core.extract
import com.shuvostechworld.genieswishlist.core.requestPermission
import com.shuvostechworld.genieswishlist.databinding.FragmentUploadProductBinding
import com.shuvostechworld.genieswishlist.db.models.Product
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class UploadProductFragment : BaseFragment<FragmentUploadProductBinding>(FragmentUploadProductBinding::inflate) {
    private lateinit var permissionsRequest: ActivityResultLauncher<Array<String>>
    private val product: Product by lazy {
        Product()
    }
    private val viewModel: ProductUploadViewModel by viewModels()
    override fun setListener() {
        permissionsRequest = getPermissionRequest()
        binding.apply {
            ivProduct.setOnClickListener {
                requestPermission(permissionsRequest, permissionList)
            }
            btnUploadProduct.setOnClickListener {
                val name = etProductName.extract()
                val price = etProductPrice.extract()
                val description = etProductDescription.extract()
                val amount = etProductAmount.extract()
                FirebaseAuth.getInstance().currentUser?.let {
                    product.apply {
                        this.sellerID = it.uid
                        this.productID = UUID.randomUUID().toString()
                        this.name = name
                        this.description = description
                        this.price = price.toDoubleOrNull() ?: 0.0
                        this.amount = amount.toIntOrNull() ?: 0
                    }
                }
                if (product.imageLink.isNotBlank()) {
                    uploadProduct(product)
                } else {
                    Toast.makeText(requireContext(), "Please select an image first", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun getPermissionRequest(): ActivityResultLauncher<Array<String>> {
        return registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            if (areAllPermissionGranted(permissionList)) {
                ImagePicker.with(this)
                    .compress(1024)
                    .maxResultSize(512, 512)
                    .createIntent { intent ->
                        startForProfileImageResult.launch(intent)
                    }
            } else {
                Toast.makeText(requireContext(), "Permissions not granted", Toast.LENGTH_SHORT).show()
            }
            }
        }

    private fun uploadProduct(product: Product) {
        viewModel.productUpload(product)
    }

  override fun allObservers() {
        viewModel.productUploadResponse.observe(viewLifecycleOwner){
            when(it) {
                is DataState.Error -> {
                    loading.dismiss()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {
                    loading.show()
                }
                is DataState.Success -> {
                    Toast.makeText(requireContext(), it.data, Toast.LENGTH_LONG).show()
                    loading.dismiss()
                }
            }
        }
    }

    companion object {
        private val permissionList = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
    }
    private val startForProfileImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult->
        val resultCode = result.resultCode
        val data = result.data
        if (resultCode == Activity.RESULT_OK) {
            val fileUri = data?.data!!
            Log.d("TAG", "$fileUri")
            binding.ivProduct.setImageURI(fileUri)
            product.imageLink = fileUri.toString()
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
        }
    }



