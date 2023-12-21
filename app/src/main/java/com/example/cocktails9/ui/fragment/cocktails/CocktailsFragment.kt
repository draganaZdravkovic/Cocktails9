package com.example.cocktails9.ui.fragment.cocktails

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktails9.R
import com.example.cocktails9.data.model.Cocktails
import com.example.cocktails9.data.model.Resource
import com.example.cocktails9.databinding.FragmentCocktailsBinding
import com.example.cocktails9.ui.activity.MainActivity
import com.example.cocktails9.ui.fragment.cocktails.recyclerview.adapter.CocktailsAdapter
import com.example.cocktails9.ui.fragment.cocktails.viewmodel.CocktailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CocktailsFragment : Fragment(R.layout.fragment_cocktails) {
    private var _binding: FragmentCocktailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CocktailsAdapter
    private val cocktailsViewModel by viewModels<CocktailsViewModel>()

    private var isSearchVisible = false
    private var query = ""

    private val args: CocktailsFragmentArgs by navArgs()
    private lateinit var filterBy: String
    private lateinit var type: String
    private var params: MutableMap<String, String> = mutableMapOf()

    private lateinit var layoutmngr: GridLayoutManager

    private lateinit var userEmail: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCocktailsBinding.inflate(inflater, container, false)
        filterBy = args.filterBy ?: ""
        type = args.type ?: ""
        params[filterBy] = type

        userEmail = (activity as MainActivity).userEmail

        if (type.isNotEmpty()) binding.tvFilter.text = type

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbarMenu()
        initRecyclerView()
        showHideFilterLabel()
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
                        val action =
                            CocktailsFragmentDirections.actionCocktailsFragmentToFilterFragment()
                        findNavController().navigate(action)
                        return true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.etSearchCocktail.doAfterTextChanged {
            query = it.toString().trim()
            binding.tvFilter.text = resources.getString(R.string.search_label, query)

            type = ""
            filterBy = ""

            showHideFilterLabel()

            cocktailsViewModel.getCocktails(query, userEmail)
        }
    }

    private fun initRecyclerView() {
        layoutmngr = GridLayoutManager(requireContext(), 2)
        binding.rvCocktails.layoutManager = layoutmngr
        adapter = CocktailsAdapter(resources)

        adapter.onFavoriteClickListener = { cocktail: Cocktails ->
            if (cocktail.isFavorite) cocktailsViewModel.removeFavorite(
                cocktail.id,
                (activity as MainActivity).userEmail
            )
            else cocktailsViewModel.addFavorite(cocktail, userEmail)

            cocktail.isFavorite = !cocktail.isFavorite
        }

        adapter.onItemClickListener = { cocktail: Cocktails ->
            val action =
                CocktailsFragmentDirections.actionCocktailsFragmentToCocktailDetailsFragment(
                    drinkID = cocktail.id,
                    isFavorite = cocktail.isFavorite
                )
            findNavController().navigate(action)
        }

        binding.rvCocktails.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            refreshCocktails()
        }

    }

    private fun refreshCocktails() {
        if (filterBy.isEmpty()) cocktailsViewModel.getCocktails(
            query,
            (activity as MainActivity).userEmail
        )
        else cocktailsViewModel.getCocktailsByCategory(params)
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
                    binding.rvCocktails.layoutManager?.smoothScrollToPosition(
                        binding.rvCocktails,
                        null,
                        0
                    )
                }
                is Resource.Loading -> showLoading(resource.isLoading)
                is Resource.Error -> showErrorDialog(resource.message)
            }
        }
        refreshCocktails()
    }

    private fun showHideFilterLabel() {
        if (query.isEmpty()) {
            if (type.isEmpty())
                binding.tvFilter.visibility = View.GONE
        } else
            binding.tvFilter.visibility = View.VISIBLE
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
}