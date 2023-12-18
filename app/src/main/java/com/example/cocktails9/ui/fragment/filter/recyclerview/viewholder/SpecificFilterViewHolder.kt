package com.example.cocktails9.ui.fragment.filter.recyclerview.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cocktails9.databinding.FilterListItemBinding

class SpecificFilterViewHolder(private val itemBinding: FilterListItemBinding) :
    ViewHolder(itemBinding.root) {

    fun bindItem(item: String) {
        itemBinding.tvFilterItem.text = item
        itemBinding.ivForwardArrow.visibility = View.GONE
    }
}