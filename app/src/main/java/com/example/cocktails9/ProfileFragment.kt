package com.example.cocktails9

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btLogout = view.findViewById<Button>(R.id.btLogout)
        btLogout.setOnClickListener {
            Toast.makeText(requireContext(), "Trying to escape?", Toast.LENGTH_SHORT).show()
        }
    }
}