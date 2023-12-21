package com.example.cocktails9.ui.fragment.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktails9.R
import com.example.cocktails9.databinding.FragmentRegisterBinding
import com.example.cocktails9.ui.fragment.authentication.viewmodel.AuthenticationViewModel
import com.example.cocktails9.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val authenticationViewModel by viewModels<AuthenticationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.btnRegister.setOnClickListener {
            if (validationSuccessful()) {
                registerUser()
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.successfully_registered),
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

    private fun registerUser() {
        val userEmail = binding.etRegisterEmail.text.trim().toString()
        authenticationViewModel.addSharedPreferencesString(
            "${Constants.NAME_KEY}$userEmail",
            binding.etName.text.trim().toString()
        )

        authenticationViewModel.addSharedPreferencesString(
            "${Constants.EMAIL_KEY}$userEmail",
            binding.etRegisterEmail.text.trim().toString()
        )

        authenticationViewModel.addSharedPreferencesString(
            "${Constants.PASSWORD_KEY}$userEmail",
            binding.etRegisterPassword.text.trim().toString()
        )
    }


    private fun validationSuccessful(): Boolean {
        if (!authenticationViewModel.isNameValid(binding.etName.text.trim().toString())) {
            binding.etName.error = resources.getString(R.string.invalid_name)
            binding.etName.requestFocus()
            return false
        }

        val email = binding.etRegisterEmail.text.trim().toString()
        if (!authenticationViewModel.isEmailValid(email)) {
            binding.etRegisterEmail.error = resources.getString(R.string.invalid_email)
            binding.etRegisterEmail.requestFocus()
            return false
        }

        if (email == authenticationViewModel.getSharedPreferencesString(
                "${Constants.EMAIL_KEY}$email",
                null
            )
        ) {
            binding.etRegisterEmail.error = resources.getString(R.string.registered)
            binding.etRegisterEmail.requestFocus()
            return false
        }

        if (!authenticationViewModel.isPasswordValid(
                binding.etRegisterPassword.text.trim().toString()
            )
        ) {
            binding.etRegisterPassword.error = resources.getString(R.string.password_validation)
            binding.etRegisterPassword.requestFocus()
            return false
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}