package com.example.cocktails9.ui.fragment.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktails9.R
import com.example.cocktails9.databinding.FragmentFavoritesBinding
import com.example.cocktails9.ui.fragment.favorites.recyclerview.adapter.FavoritesAdapter
import com.example.cocktails9.ui.fragment.favorites.viewmodel.FavoritesViewModel
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
    }


    private fun initObservers() {

        favoritesViewModel.favoritesListLiveData.observe(viewLifecycleOwner) {
            if(it.isEmpty())
                showNotFound()
            adapter.submitList(it)
        }
    }

    private fun showNotFound() {
        binding.tvEmpty.visibility = View.VISIBLE
        binding.tvEmpty.text = resources.getString(R.string.no_cocktails_found)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}