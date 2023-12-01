package com.example.cocktails9

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktails9.databinding.FragmentCocktailsBinding

class CocktailsFragment : Fragment(R.layout.fragment_cocktails) {
    private var _binding: FragmentCocktailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var cocktailsList: MutableList<Cocktails>
    private lateinit var cocktailsAdapter: CocktailsAdapter

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        binding.rvCocktails.layoutManager = GridLayoutManager(requireContext(), 2)

        cocktailsList = addDataToList()

        cocktailsAdapter = CocktailsAdapter(cocktailsList)
        binding.rvCocktails.adapter = cocktailsAdapter
    }

    private fun addDataToList(): MutableList<Cocktails> {
        cocktailsList = mutableListOf()
        cocktailsList.add(Cocktails(R.drawable.c1, "110 in the shade"))
        cocktailsList.add(Cocktails(R.drawable.c2, "151 Florida Bushwacker"))

        cocktailsList.add(Cocktails(R.drawable.c1, "110 in the shade"))
        cocktailsList.add(Cocktails(R.drawable.c2, "151 Florida Bushwacker"))

        cocktailsList.add(Cocktails(R.drawable.c1, "110 in the shade"))
        cocktailsList.add(Cocktails(R.drawable.c2, "151 Florida Bushwacker"))

        return cocktailsList
    }
}