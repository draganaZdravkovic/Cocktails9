package com.example.cocktails9.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktails9.R
import com.example.cocktails9.adapter.CocktailsAdapter
import com.example.cocktails9.databinding.FragmentCocktailsBinding
import com.example.cocktails9.model.Resource
import com.example.cocktails9.viewmodel.CocktailsViewModel
import com.example.cocktails9.viewmodel.CocktailsViewModelFactory


class CocktailsFragment : Fragment(R.layout.fragment_cocktails) {
    private var _binding: FragmentCocktailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CocktailsAdapter
    private lateinit var cocktailsViewModel: CocktailsViewModel

    private var isSearchVisible = false
    private var query = ""

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

        initToolbarMenu()
        initRecyclerView()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initToolbarMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.miSearch -> {
                        showSearch()
                        return true
                    }
                    R.id.miFilter -> {
                        Toast.makeText(context, "Filter", Toast.LENGTH_SHORT).show()
                        return true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.etSearchCocktail.doAfterTextChanged {
            query = it.toString().trim()
            cocktailsViewModel.getCocktails(query)
        }
    }

    private fun showSearch() {
        isSearchVisible = !isSearchVisible
        if (isSearchVisible) {
            binding.etSearchCocktail.visibility = View.VISIBLE
            binding.etSearchCocktail.requestFocus()
        } else {
            binding.etSearchCocktail.visibility = View.GONE
        }
    }

    private fun initRecyclerView() {
        binding.rvCocktails.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = CocktailsAdapter()
        binding.rvCocktails.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            rearrangeItems()
        }
    }

    private fun rearrangeItems() {
        cocktailsViewModel.getCocktails(query)
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