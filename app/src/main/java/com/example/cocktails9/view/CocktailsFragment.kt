package com.example.cocktails9.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktails9.R
import com.example.cocktails9.adapter.CocktailsAdapter
import com.example.cocktails9.databinding.FragmentCocktailsBinding
import com.example.cocktails9.model.Cocktails
import com.example.cocktails9.model.Resource
import com.example.cocktails9.viewmodel.CocktailsViewModel
import com.example.cocktails9.viewmodel.CocktailsViewModelFactory

class CocktailsFragment : Fragment(R.layout.fragment_cocktails) {
    private var _binding: FragmentCocktailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CocktailsAdapter

    private lateinit var cocktailsViewModel: CocktailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cocktailsViewModel = ViewModelProvider(
            this,
            CocktailsViewModelFactory()
        )[CocktailsViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCocktailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initObservers()
    }

    private fun initRecyclerView() {
        binding.rvCocktails.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = CocktailsAdapter()
        binding.rvCocktails.adapter = adapter
    }

    private fun initObservers() {
        cocktailsViewModel.getCocktailsList.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    showLoading(false)
                    adapter.submitList(resource.data)
                }
                is Resource.Loading -> {
                    showLoading(resource.isLoading)
                }
                is Resource.Error -> {
                    showAlertDialog(resource.message)
                }
            }
        }
        cocktailsViewModel.getCocktails()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showAlertDialog(message: String) {
        binding.rvCocktails.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        AlertDialog.Builder(context)
            .setTitle("Cocktails9 Error")
            .setMessage(message)
            .setPositiveButton("OK") { _, _ -> }
            .show()
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.rvCocktails.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.rvCocktails.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }
}