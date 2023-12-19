package com.example.cocktails9.ui.fragment.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cocktails9.R
import com.example.cocktails9.databinding.FragmentProfileBinding
import com.example.cocktails9.ui.activity.AuthenticationActivity
import com.example.cocktails9.ui.activity.MainActivity
import com.example.cocktails9.utils.Constants

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences

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

        sharedPreferences =
            activity?.getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
                ?: return

        val userEmail = (activity as MainActivity).userEmail

        binding.etEmail.setText(
            sharedPreferences.getString(
                Constants.EMAIL_KEY + userEmail,
                resources.getString(R.string.example_levi9_com)
            )
        )
        binding.etPassword.setText(
            sharedPreferences.getString(
                "${Constants.PASSWORD_KEY}$userEmail",
                resources.getString(R.string.intern2023)
            )
        )

        binding.btLogout.setOnClickListener {
            startActivity(Intent(requireContext(), AuthenticationActivity::class.java))
            activity?.finish()
        }

        binding.tbEditPassword.setOnClickListener {
            if (binding.tbEditPassword.isChecked) {
                binding.etPassword.isEnabled = true
                binding.etPassword.requestFocus()
            } else {
                binding.etPassword.isEnabled = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}