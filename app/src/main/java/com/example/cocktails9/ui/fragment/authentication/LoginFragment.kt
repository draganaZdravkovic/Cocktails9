package com.example.cocktails9.ui.fragment.authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktails9.R
import com.example.cocktails9.databinding.FragmentLoginBinding
import com.example.cocktails9.ui.activity.MainActivity
import com.example.cocktails9.ui.fragment.authentication.viewmodel.AuthenticationViewModel
import com.example.cocktails9.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val authenticationViewModel by viewModels<AuthenticationViewModel>()

    private lateinit var email: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            if (validationSuccessful()) {
                if (isRegistered()) {
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    intent.putExtra(Constants.EMAIL_KEY, email)
                    startActivity(intent)
                    activity?.finish()
                } else {
                    Toast.makeText(
                        context,
                        resources.getString(R.string.not_registered),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun validationSuccessful(): Boolean {
        val email = binding.etLoginEmail.text.trim()
        if (email.isEmpty()) {
            binding.etLoginEmail.error = resources.getString(R.string.email_validation_empty)
            binding.etLoginEmail.requestFocus()
            return false
        }

        if (binding.etLoginPassword.text.isNullOrEmpty()) {
            binding.etLoginPassword.error = resources.getString(R.string.password_validation_empty)
            binding.etLoginPassword.requestFocus()
            return false
        }

        return true
    }

    private fun isRegistered(): Boolean {
        email = authenticationViewModel.getSharedPreferencesString(
            "${Constants.EMAIL_KEY}${binding.etLoginEmail.text.trim()}", null
        )
        val password =
            authenticationViewModel.getSharedPreferencesString(
                "${Constants.PASSWORD_KEY}$email",
                null
            )

        if (email.isEmpty()
            || password.isEmpty()
            || (binding.etLoginEmail.text.toString() != email)
            || (binding.etLoginPassword.text.toString() != password)
        )
            return false

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}