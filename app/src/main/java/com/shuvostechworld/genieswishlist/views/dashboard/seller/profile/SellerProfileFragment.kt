package com.shuvostechworld.genieswishlist.views.dashboard.seller.profile

import android.Manifest
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import coil.load
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.shuvostechworld.genieswishlist.base.BaseFragment
import com.shuvostechworld.genieswishlist.core.DataState
import com.shuvostechworld.genieswishlist.core.areAllPermissionGranted
import com.shuvostechworld.genieswishlist.core.extract
import com.shuvostechworld.genieswishlist.core.requestPermission
import com.shuvostechworld.genieswishlist.databinding.FragmentSellerProfileBinding
import com.shuvostechworld.genieswishlist.db.models.Profile
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SellerProfileFragment : BaseFragment<FragmentSellerProfileBinding>(FragmentSellerProfileBinding::inflate) {
    private lateinit var permissionsRequest: ActivityResultLauncher<Array<String>>
    private val viewModel: SellerProfileViewModel by viewModels()
    private var sellerProfile: Profile? = null
    private var hasLocalImage: Boolean = false
    override fun setListener() {
        FirebaseAuth.getInstance().currentUser?.let {
            viewModel.getUserByUserID(it.uid)
        }
        permissionsRequest = getPermissionRequest()
        binding.apply {
            ivProfile.setOnClickListener {
                requestPermission(permissionsRequest, permissionList)
            }
            btnUpdate.setOnClickListener {
                val name = etName.extract()
                val email = etEmail.extract()

                sellerProfile.apply {
                    this?.name = name
                    this?.email = email
                }
                updateProfile(sellerProfile)

            }
        }

    }

    fun updateProfile(sellerProfile: Profile?) {
        sellerProfile?.let {
            viewModel.updateProfile(it, hasLocalImage)
        }
    }

    override fun allObservers() {
        viewModel.profileUpdateResponse.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Error -> {
                    loading.dismiss()
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

        viewModel.logedInUserResponse.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Error -> {
                    loading.dismiss()
                }
                is DataState.Loading -> {
                    loading.show()
                }
                is DataState.Success -> {
                    sellerProfile = it.data
                    setProfileData(sellerProfile)
                    loading.dismiss()
                }
            }
        }

    }

    private fun setProfileData (sellerProfile: Profile?) {
        hasLocalImage = sellerProfile?.userImage.isNullOrBlank()
        binding.apply {
            etName.setText(sellerProfile?.name)
            etEmail.setText(sellerProfile?.email)
            ivProfile.load(sellerProfile?.userImage)
        }
    }

    private fun getPermissionRequest(): ActivityResultLauncher<Array<String>> {
        return registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (areAllPermissionGranted(permissionList)) {
                ImagePicker.with(this)
                    .compress(1024)
                    .maxResultSize(512, 512)
                    .createIntent { intent ->
                        startForProfileImageResult.launch(intent)
                    }

            } else {
                Toast.makeText(requireContext(), "Request not granted ", Toast.LENGTH_LONG).show()
            }

        }
    }

    companion object {
        private val permissionList = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    }
    private val startForProfileImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult->
        val resultCode = result.resultCode
        val data = result.data
        if (resultCode == Activity.RESULT_OK) {
            val fileUri = data?.data!!
            Log.d("TAG", "$fileUri")
            binding.ivProfile.setImageURI(fileUri)
            sellerProfile?.userImage = fileUri.toString()
            hasLocalImage = true
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
        }

    }

}

