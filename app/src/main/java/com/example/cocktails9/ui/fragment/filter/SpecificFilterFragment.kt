package com.example.cocktails9.ui.fragment.filter

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktails9.R
import com.example.cocktails9.data.model.Resource
import com.example.cocktails9.databinding.FragmentSpecificFilterBinding
import com.example.cocktails9.ui.fragment.filter.recyclerview.adapter.SpecificFilterAdapter
import com.example.cocktails9.ui.fragment.filter.viewmodel.SpecificFilterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpecificFilterFragment : Fragment(R.layout.fragment_specific_filter) {

    private var _binding: FragmentSpecificFilterBinding? = null
    private val binding get() = _binding!!

    private lateinit var filterBy: String
    private lateinit var title: String
    private val args: SpecificFilterFragmentArgs by navArgs()

    private lateinit var adapter: SpecificFilterAdapter
    private val specificFilterViewModel by viewModels<SpecificFilterViewModel>()
    private var params: MutableMap<String, String> = mutableMapOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpecificFilterBinding.inflate(inflater, container, false)

        filterBy = args.filterBy
        title = args.title

        (activity as? AppCompatActivity)?.supportActionBar?.title = title
        binding.tvFilterBy.text = buildString {
            append(binding.tvFilterBy.text)
            append(" ")
            append(title)
        }

        params[filterBy] = "list"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initObservers()
    }

    private fun initObservers() {
        specificFilterViewModel.getFilterList.observe(viewLifecycleOwner) { resource ->
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
        specificFilterViewModel.getFilterList(params)
    }

    private fun initRecyclerView() {
        binding.rvFilterBy.layoutManager = LinearLayoutManager(requireContext())
        adapter = SpecificFilterAdapter()

        adapter.onItemClickListener = { item: String ->
            val action =
                SpecificFilterFragmentDirections.actionSpecificFilterFragmentToCocktailsFragment(
                    filterBy = filterBy,
                    type = item
                )
            findNavController().navigate(action)
        }

        binding.rvFilterBy.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            refreshCocktails()
        }
    }

    private fun refreshCocktails() {
        specificFilterViewModel.getFilterList(params)
    }

    private fun showNotFound() {
        binding.tvEmpty.visibility = View.VISIBLE
        binding.tvEmpty.text = resources.getString(R.string.no_cocktails_found)
    }

    private fun showErrorDialog(message: String) {
        binding.rvFilterBy.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        AlertDialog.Builder(context)
            .setTitle(resources.getString(R.string.cocktails_error))
            .setMessage(message)
            .setPositiveButton(resources.getString(R.string.ok)) { _, _ -> }
            .show()
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.rvFilterBy.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            binding.tvEmpty.visibility = View.GONE
        } else {
            binding.rvFilterBy.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}