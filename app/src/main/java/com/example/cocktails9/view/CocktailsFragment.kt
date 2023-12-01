package com.example.cocktails9.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktails9.R
import com.example.cocktails9.adapter.CocktailsAdapter
import com.example.cocktails9.api.ApiInterface
import com.example.cocktails9.api.ApiUtilities
import com.example.cocktails9.databinding.FragmentCocktailsBinding
import com.example.cocktails9.model.Cocktails
import com.example.cocktails9.repository.CocktailsRepository
import com.example.cocktails9.viewmodel.CocktailsViewModel
import com.example.cocktails9.viewmodel.CocktailsViewModelFactory

class CocktailsFragment : Fragment(R.layout.fragment_cocktails) {
    private var _binding: FragmentCocktailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var cocktailsList: MutableList<Cocktails>
    private lateinit var cocktailsAdapter: CocktailsAdapter

    private lateinit var cocktailsViewModel: CocktailsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val apiInterface = ApiUtilities.getInstance().create(ApiInterface::class.java)
        val cocktailsRepo = CocktailsRepository(apiInterface)
        cocktailsViewModel = ViewModelProvider(
            this,
            CocktailsViewModelFactory(cocktailsRepo)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        binding.rvCocktails.layoutManager = GridLayoutManager(requireContext(), 2)

        cocktailsList = addDataToList()

        cocktailsViewModel.getCocktailsList.observe(this) { it ->
            binding.rvCocktails.adapter = CocktailsAdapter(it.body()?.list!!)
        }
    }

    private fun addDataToList(): MutableList<Cocktails> {
        cocktailsList = mutableListOf()
        cocktailsList.add(Cocktails("1", R.drawable.c1.toString(), "110 in the shade", "Al"))
        cocktailsList.add(
            Cocktails(
                "2",
                R.drawable.c2.toString(),
                "151 Florida Bushwacker",
                "NonAl"
            )
        )

        cocktailsList.add(Cocktails("1", R.drawable.c1.toString(), "110 in the shade", "Al"))
        cocktailsList.add(
            Cocktails(
                "2",
                R.drawable.c2.toString(),
                "151 Florida Bushwacker",
                "NonAl"
            )
        )

        cocktailsList.add(Cocktails("1", R.drawable.c1.toString(), "110 in the shade", "Al"))
        cocktailsList.add(
            Cocktails(
                "2",
                R.drawable.c2.toString(),
                "151 Florida Bushwacker",
                "NonAl"
            )
        )

        return cocktailsList
    }
}