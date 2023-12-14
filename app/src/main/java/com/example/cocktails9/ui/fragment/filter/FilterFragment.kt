package com.example.cocktails9.ui.fragment.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cocktails9.R
import com.example.cocktails9.databinding.FragmentFilterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : Fragment(R.layout.fragment_filter) {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListView()
    }

    private fun initListView() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.filter_list_item,
            R.id.tvFilterItem,
            resources.getStringArray(R.array.filterList)
        )
        binding.lvFilter.adapter = adapter

        binding.lvFilter.setOnItemClickListener { _, _, position, _ ->
            val filterBy: String
            val title: String
            when (position) {
                0 -> {
                    filterBy = "a"
                    title = adapter.getItem(position).toString()
                }
                1 -> {
                    filterBy = "c"
                    title = adapter.getItem(position).toString()
                }
                2 -> {
                    filterBy = "g"
                    title = adapter.getItem(position).toString()
                }
                3 -> {
                    filterBy = "i"
                    title = adapter.getItem(position).toString()
                }
                else -> {
                    filterBy = "a"
                    title = adapter.getItem(position).toString()
                }
            }

            val action = FilterFragmentDirections.actionFilterFragmentToSpecificFilterFragment2(
                filterBy = filterBy,
                title = title
            )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}