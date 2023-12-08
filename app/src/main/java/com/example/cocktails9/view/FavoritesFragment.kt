package com.example.cocktails9.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktails9.R
import com.example.cocktails9.adapter.FavoritesAdapter
import com.example.cocktails9.databinding.FragmentFavoritesBinding
import com.example.cocktails9.viewmodel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FavoritesAdapter
    private val favoritesViewModel by viewModels<FavoritesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initObservers()
    }

    private fun initRecyclerView() {
        binding.rvCocktails.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = FavoritesAdapter()
        binding.rvCocktails.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            refreshCocktails()
        }
    }

    private fun refreshCocktails() {
        favoritesViewModel.getFavorites()
    }

    private fun initObservers() {
        favoritesViewModel.getFavoritesList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        favoritesViewModel.getFavorites()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}