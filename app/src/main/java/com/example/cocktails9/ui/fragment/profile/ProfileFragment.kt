package com.example.cocktails9.ui.fragment.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.cocktails9.R
import com.example.cocktails9.databinding.FragmentProfileBinding
import com.example.cocktails9.ui.activity.AuthenticationActivity
import com.example.cocktails9.ui.activity.MainActivity
import com.example.cocktails9.ui.fragment.authentication.viewmodel.AuthenticationViewModel
import com.example.cocktails9.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val authenticationViewModel by viewModels<AuthenticationViewModel>()

    private lateinit var userEmail: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userEmail = (activity as MainActivity).userEmail

        setPhotoEditing()
        populateUIWithData()
        setClickListeners()
    }

    private fun setPhotoEditing() {
        val userPhoto = authenticationViewModel.getSharedPreferencesString(
            "${Constants.PHOTO_KEY}$userEmail",
            null
        )

        if (userPhoto.isNotBlank()) {
            Glide.with(binding.ivProfile.context).load(userPhoto).into(binding.ivProfile)
        }

        val changeImage = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val imgUri = data?.data
                authenticationViewModel.addSharedPreferencesString(
                    "${Constants.PHOTO_KEY}$userEmail",
                    imgUri.toString()
                )
                Glide.with(binding.ivProfile.context).load(imgUri).into(binding.ivProfile)
            }
        }

        binding.fbtEditPhoto.setOnClickListener {
            val pickImage =
                Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            changeImage.launch(pickImage)
        }
    }

    private fun setClickListeners() {
        binding.tbEditPassword.setOnClickListener {
            if (binding.tbEditPassword.isChecked) {
                binding.etPassword.isEnabled = true
                binding.etPassword.requestFocus()
            } else {
                val password = binding.etPassword.text.trim().toString()

                binding.etPassword.isEnabled = false

                if (authenticationViewModel.isPasswordValid(password)) {
                    authenticationViewModel.addSharedPreferencesString(
                        "${Constants.PASSWORD_KEY}$userEmail",
                        password
                    )
                } else {
                    binding.etPassword.error = resources.getString(R.string.password_validation)
                    binding.etPassword.requestFocus()
                }
            }
        }

        binding.tbEditName.setOnClickListener {
            if (binding.tbEditName.isChecked) {
                binding.etName.isEnabled = true
                binding.etName.requestFocus()
            } else {
                val name = binding.etName.text.trim().toString()

                binding.etName.isEnabled = false

                if (authenticationViewModel.isNameValid(name)) {
                    authenticationViewModel.addSharedPreferencesString(
                        "${Constants.NAME_KEY}$userEmail",
                        name
                    )
                } else {
                    binding.etName.error = resources.getString(R.string.invalid_name)
                    binding.etName.requestFocus()
                }
            }
        }

        binding.btLogout.setOnClickListener {
            startActivity(Intent(requireContext(), AuthenticationActivity::class.java))
            activity?.finish()
        }
    }

    private fun populateUIWithData() {
        binding.etName.setText(
            authenticationViewModel.getSharedPreferencesString(
                Constants.NAME_KEY + userEmail,
                ""
            )
        )

        binding.etEmail.setText(
            authenticationViewModel.getSharedPreferencesString(
                Constants.EMAIL_KEY + userEmail,
                resources.getString(R.string.example_levi9_com)
            )
        )
        binding.etPassword.setText(
            authenticationViewModel.getSharedPreferencesString(
                "${Constants.PASSWORD_KEY}$userEmail",
                resources.getString(R.string.intern2023)
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}