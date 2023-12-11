package com.example.cocktails9.ui.fragment.cocktails

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktails9.R
import com.example.cocktails9.data.model.Cocktails
import com.example.cocktails9.data.model.Resource
import com.example.cocktails9.databinding.FragmentCocktailsBinding
import com.example.cocktails9.ui.fragment.cocktails.recyclerview.adapter.CocktailsAdapter
import com.example.cocktails9.ui.fragment.cocktails.viewmodel.CocktailsViewModel
import com.example.cocktails9.ui.fragment.favorites.viewmodel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CocktailsFragment : Fragment(R.layout.fragment_cocktails),
    CocktailsAdapter.FavoriteClickListener {
    private var _binding: FragmentCocktailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CocktailsAdapter
    private val cocktailsViewModel by viewModels<CocktailsViewModel>()
    private val favoritesViewModel by viewModels<FavoritesViewModel>()

    private var isSearchVisible = false
    private var query = ""

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
                        showHideSearch()
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

    private fun initRecyclerView() {
        binding.rvCocktails.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = CocktailsAdapter(this)
        binding.rvCocktails.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            refreshCocktails()
        }
    }

    private fun refreshCocktails() {
        cocktailsViewModel.getCocktails(query)
    }

    private fun initObservers() {
        cocktailsViewModel.getCocktailsList.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    if (resource.data.isEmpty())
                        showNotFound()
                    else
                        showLoading(false)

                    adapter.submitList(resource.data)
                }
                is Resource.Loading -> showLoading(resource.isLoading)
                is Resource.Error -> showErrorDialog(resource.message)
            }
        }
        cocktailsViewModel.getCocktails()
    }

    private fun showNotFound() {
        binding.tvEmpty.visibility = View.VISIBLE
        binding.tvEmpty.text = resources.getString(R.string.no_cocktails_found)
    }

    private fun showHideSearch() {
        isSearchVisible = !isSearchVisible
        if (isSearchVisible) {
            binding.etSearchCocktail.visibility = View.VISIBLE
            binding.etSearchCocktail.requestFocus()
        } else {
            binding.etSearchCocktail.visibility = View.GONE
        }
    }

    private fun showErrorDialog(message: String) {
        binding.rvCocktails.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        AlertDialog.Builder(context)
            .setTitle(resources.getString(R.string.cocktails_error))
            .setMessage(message)
            .setPositiveButton(resources.getString(R.string.ok)) { _, _ -> }
            .show()
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.rvCocktails.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            binding.tvEmpty.visibility = View.GONE
        } else {
            binding.rvCocktails.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onFavoriteClick(cocktail: Cocktails, toAdd: Boolean) {
        if (toAdd) favoritesViewModel.addFavorite(cocktail)
        else favoritesViewModel.removeFavorite(cocktail)
    }
}